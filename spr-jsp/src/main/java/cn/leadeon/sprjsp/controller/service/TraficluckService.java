package cn.leadeon.sprjsp.controller.service;

import cn.leadeon.sprjsp.comm.ExportUtil;
import cn.leadeon.sprjsp.entity.TraficLuck;
import cn.leadeon.sprjsp.mapper.TraficluckMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @description:
 * @author: he.l
 * @create: 2019-07-23 11:30
 **/
@Service
public class TraficluckService {
    private static final Logger logger = LoggerFactory.getLogger(TraficluckService.class);
    @Autowired
    private TraficluckMapper traficluckMapper;

    public List<TraficLuck> find(Map<String,Object> params){
        return  traficluckMapper.find(params);
    }

    public Integer total(Map<String,Object> params){
        return traficluckMapper.total(params);
    }

    public void export(int total,Map<String,Object> params){
        long start = System.currentTimeMillis();
        int count = total/500000;
        if(total%500000!=0){
            count +=1;
        }
        List<Thread> list = new ArrayList<Thread>();
        for(int i=0;i<count;i++){
            Thread exportThread = new Thread(new ExportThread(params,i*500000,(i+1)*500000-1,i+1));
            list.add(exportThread);
        }
        ExecutorService pool =  ExportUtil.fixedThreadExport(total,list);
        pool.shutdown();
        while (true) {
            if (pool.isTerminated()) { // 计算耗时
                long time = System.currentTimeMillis() - start;
                logger.info("导出消耗时间："+time);
                break;
            }
        }
    }
    class ExportThread implements Runnable{
        private Integer pageSize = 5000;
        private Map<String,Object> params;
        private Integer startSize;
        private Integer endSize;
        private Integer tableNum;

        public  ExportThread(Map<String,Object> params,Integer startSize,Integer endSize,Integer tableNum){
            this.params = new HashMap<>();
            this.params.putAll(params);
            this.endSize = endSize;
            this.startSize = startSize;
            this.tableNum = tableNum;
        }

        @Override
        public void run() {
            //输出流
            BufferedWriter bw = null;
            try {
                // TODO 写入单个文件
                String fileName = "本网用户红包领取情况"+tableNum + ".csv";
                String tmpPath = ResourceUtils.getURL("classpath:").getPath()+"/tempdir";
                // 组装文件路径
                File tmpDirFile = new File(tmpPath);
                if (!tmpDirFile.exists())
                {
                    tmpDirFile.mkdirs();
                }
                // 创建文件
                File tmpFile = new File(tmpPath, fileName);
                if (!tmpFile.exists())
                {
                    tmpFile.createNewFile();
                }
                // 写入文件信息
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile), "GBK"));
                //创建表头
                String header = "子红包ID,领取时间,领取人号码,领取人省市,子红包大小（MB）,子红包状态,充值订单号,红包类型,活动ID,红包来源";
                bw.write(header);
                bw.newLine();
                //分页查询 数据 并且 写入数据
                int pageNum = (endSize-startSize+1)/pageSize;
                for(int i=0;i<pageNum;i++){
                    params.put("startOffset",startSize+i*pageSize);
                    params.put("pageSize",pageSize);
                    List<TraficLuck> resultDate = traficluckMapper.find(params);
                    if(null!=resultDate&&resultDate.size()>0){
                        for(TraficLuck entity : resultDate){
                            StringBuffer cont = new StringBuffer();
                            cont.append("\t"+entity.getId() + ",");
                            cont.append("\t"+entity.getReceiveNum()+",");
                            cont.append("\t"+entity.getReceiveType()+",");
                            cont.append(entity.getOpenId()+"/"+entity.getOpenId()+",");
                            cont.append("\t"+entity.getGiveOutNum()+",");
                            cont.append("\t"+(null == entity.getPurchase() ? "" :entity.getPurchase())+",");
                            cont.append("\t"+entity.getLuckId()+",");
                            cont.append("\t"+entity.getDealTime()+",");
                            cont.append(entity.getSubsetId()+"/"+entity.getSubsetId()+",");
                            cont.append("\t"+entity.getMd5SubsetId()+",");
                            bw.write(cont.toString());
                            bw.newLine();
                        }
                    }else{
                        bw.write("暂无数据");
                        bw.newLine();
                    }
                }
            }catch (Exception e){
                logger.error("本网用户红包领取情况导出异常--->>>" + e.getMessage(), e);
            }finally{
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    logger.error("本网用户红包领取情况导出IOException异常" + e.getMessage(), e);
                }
            }
            logger.info("本网用户红包领取情况"+tableNum+".csv  完成");
        }
    }
}
