package cn.leadeon.sprelsearch.controller;

import cn.leadeon.sprelsearch.entity.BookBean;
import cn.leadeon.sprelsearch.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @description:
 * @author: he.l
 * @create: 2019-08-08 14:55
 **/
@RestController
public class ElasticController {
    protected final BookService bookService;

    @Autowired
    public ElasticController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/book/{id}")
    @ResponseBody
    public BookBean getBookById(@PathVariable String id){
        Optional<BookBean> opt =bookService.findById(id);
        BookBean book=opt.get();
        System.out.println(book);
        return book;
    }

    @RequestMapping("/save")
    @ResponseBody
    public void Save(){
        BookBean book=new BookBean("1","ES入门教程","程裕强","2018-10-01");
        System.out.println(book);
        bookService.save(book);
    }
}
