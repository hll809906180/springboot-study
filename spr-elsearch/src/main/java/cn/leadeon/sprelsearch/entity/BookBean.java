package cn.leadeon.sprelsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @description:
 * @author: he.l
 * @create: 2019-08-08 14:34
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "book", type = "_doc")
public class BookBean {
    @Id
    private String id;
    private String title;
    private String author;
    private String postDate;


}
