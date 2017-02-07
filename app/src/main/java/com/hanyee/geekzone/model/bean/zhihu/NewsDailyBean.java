package com.hanyee.geekzone.model.bean.zhihu;

import java.util.List;

/**
 * Created by Hanyee on 16/10/13.
 */
public class NewsDailyBean {

    /**
     * date : 20161013
     * stories : [{"title":"想要用相机去关怀，却成了对被摄对象的攫取与掠夺","ga_prefix":"101311","images":["http://pic2.zhimg.com/d9aa7846f52f26e9a6845e9168253b1d.jpg"],"multipic":true,"type":0,"id":8819656},{"images":["http://pic2.zhimg.com/a721ee3de7838714e114b7764cb5a251.jpg"],"type":0,"id":8817495,"ga_prefix":"101310","title":"看到「激素」就抗拒，也许会错过安全的治疗方案"},{"images":["http://pic1.zhimg.com/2eaccc4d5ae2f99c1f3100f22a339324.jpg"],"type":0,"id":8879162,"ga_prefix":"101309","title":"来学习一些说谎的原理，和识破谎言的小技巧"},{"images":["http://pic1.zhimg.com/665396be203fb5654fc593ca74f08f80.jpg"],"type":0,"id":8855713,"ga_prefix":"101308","title":"都说醒酒很重要，可醒多久才算是「醒好了」？"},{"images":["http://pic3.zhimg.com/8e05d698209740a708aaab49ad818cca.jpg"],"type":0,"id":8878106,"ga_prefix":"101307","title":"为了终止儿童失踪，我发起了中国的「安珀警报」"},{"images":["http://pic3.zhimg.com/39802deefbdb123af9fa19b8bc90352a.jpg"],"type":0,"id":8877570,"ga_prefix":"101307","title":"曾经有一个纠错的机会摆在本届诺奖评委面前"},{"images":["http://pic2.zhimg.com/4f7e7a0ea333604515653bc8045ed66d.jpg"],"type":0,"id":8876502,"ga_prefix":"101307","title":"儿童的性教育中，有什么细节最容易被我们忽视掉？"},{"images":["http://pic3.zhimg.com/98e503d9dcec3df82e29f0351874776e.jpg"],"type":0,"id":8879592,"ga_prefix":"101307","title":"读读日报 24 小时热门 TOP 5 · 《西部世界》与电子游戏"},{"images":["http://pic2.zhimg.com/a977b6ab93db069638ffd737b1cba59d.jpg"],"type":0,"id":8877413,"ga_prefix":"101306","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic3.zhimg.com/d145335cca75d30d3fd19123e3f5ee36.jpg","type":0,"id":8879162,"ga_prefix":"101309","title":"来学习一些说谎的原理，和识破谎言的小技巧"},{"image":"http://pic1.zhimg.com/413b37861ef97edcd1c3ef1c9f2f6fbc.jpg","type":0,"id":8878106,"ga_prefix":"101307","title":"为了终止儿童失踪，我发起了中国的「安珀警报」"},{"image":"http://pic3.zhimg.com/594a11f753d36270b698f1ca97ea1a1e.jpg","type":0,"id":8877570,"ga_prefix":"101307","title":"曾经有一个纠错的机会摆在本届诺奖评委面前"},{"image":"http://pic3.zhimg.com/6002bc906b7aa43b44ff1a862651dc66.jpg","type":0,"id":8879592,"ga_prefix":"101307","title":"读读日报 24 小时热门 TOP 5 · 《西部世界》与电子游戏"},{"image":"http://pic3.zhimg.com/3b04e1791050bbc513e5a6071abea1a6.jpg","type":0,"id":8878108,"ga_prefix":"101217","title":"知乎好问题 · 秋冬季节如何穿毛衣才好看？"}]
     */

    private String date;
    /**
     * title : 想要用相机去关怀，却成了对被摄对象的攫取与掠夺
     * ga_prefix : 101311
     * images : ["http://pic2.zhimg.com/d9aa7846f52f26e9a6845e9168253b1d.jpg"]
     * multipic : true
     * type : 0
     * id : 8819656
     */

    private List<StoriesBean> stories;
    /**
     * image : http://pic3.zhimg.com/d145335cca75d30d3fd19123e3f5ee36.jpg
     * type : 0
     * id : 8879162
     * ga_prefix : 101309
     * title : 来学习一些说谎的原理，和识破谎言的小技巧
     */

    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class TopStoriesBean {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
