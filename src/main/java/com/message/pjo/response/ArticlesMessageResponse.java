package com.message.pjo.response;

import java.util.List;

import com.message.pjo.request.BaseMessage;
import com.message.pjo.response.unit.item;

public class ArticlesMessageResponse extends BaseMessage {
	// 图文消息个数，限制为10条以内  
    private int ArticleCount;  
    // 多条图文消息信息，默认第一个item为大图  
    private List<item> Articles;  
  
    public int getArticleCount() {  
        return ArticleCount;  
    }  
  
    public void setArticleCount(int articleCount) {  
        ArticleCount = articleCount;  
    }  
  
    public List<item> getArticles() {  
        return Articles;  
    }  
  
    public void setArticles(List<item> articles) {  
        Articles = articles;  
    }  
}
