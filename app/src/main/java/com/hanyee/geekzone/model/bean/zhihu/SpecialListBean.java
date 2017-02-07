package com.hanyee.geekzone.model.bean.zhihu;

import java.util.List;

/**
 * Created by Hanyee on 16/10/25.
 */

public class SpecialListBean {

    /**
     * timestamp : 1475676000
     * stories : [{"images":["http://pic2.zhimg.com/ae852b938565b7c7e10a4e896d3d260d.jpg"],"date":"20161024","display_date":"10 月 24 日","id":8916065,"title":"小事 · 不如我们重新来过"},{"images":["http://pic1.zhimg.com/8965e9af29d1458ffb6af55d878b7b98.jpg"],"date":"20161023","display_date":"10 月 23 日","id":8911065,"title":"小事 · 必须结婚，不结不行"},{"images":["http://pic3.zhimg.com/2e2b6bbeceaa54446b649b98d6026e56.jpg"],"date":"20161022","display_date":"10 月 22 日","id":8908603,"title":"小事 · 我们没有那么烂"},{"images":["http://pic2.zhimg.com/f1f06f1f8c8c79aaea501824c9dd21ed.jpg"],"date":"20161021","display_date":"10 月 21 日","id":8907886,"title":"小事 · 第一次花这么多钱"},{"images":["http://pic4.zhimg.com/8a30e405a887c111b14b3d83d608709f.jpg"],"date":"20161020","display_date":"10 月 20 日","id":8904895,"title":"小事 · 那家烧烤摊，大人不让去"},{"images":["http://pic3.zhimg.com/7db9ecd56080528a52b12179e61d03d2.jpg"],"date":"20161019","display_date":"10 月 19 日","id":8894844,"title":"小事 · 昼夜颠倒，持续一年"},{"images":["http://pic2.zhimg.com/d76f63077665ae41aaed0e202b66a2e1.jpg"],"date":"20161018","display_date":"10 月 18 日","id":8895202,"title":"小事 · 仅次于分娩"},{"images":["http://pic1.zhimg.com/927aa3342ea02b467a87bbe413e9fce4.jpg"],"date":"20161017","display_date":"10 月 17 日","id":8864587,"title":"小事 · 说了你也听不懂"},{"images":["http://pic1.zhimg.com/1f9248da3719f0bd124553cdc278353c.jpg"],"date":"20161016","display_date":"10 月 16 日","id":8874874,"title":"小事 · 那一瞬间只能冷静"},{"images":["http://pic2.zhimg.com/591b83bfd299dc33ee9b71f1430dcc91.jpg"],"date":"20161015","display_date":"10 月 15 日","id":8875165,"title":"小事 · 你说巧不巧"},{"images":["http://pic3.zhimg.com/4cc6811799aa833a226371c6fe21451a.jpg"],"date":"20161014","display_date":"10 月 14 日","id":8882871,"title":"小事 · 有些努力毫无意义"},{"images":["http://pic3.zhimg.com/e572c5f669dd50217444de2bdfa991c6.jpg"],"date":"20161013","display_date":"10 月 13 日","id":8882226,"title":"小事 · 发誓再也不回头"},{"images":["http://pic3.zhimg.com/313d48684007df9f1bd967df11fc9eee.jpg"],"date":"20161012","display_date":"10 月 12 日","id":8868469,"title":"小事 · 谜之尴尬"},{"images":["http://pic1.zhimg.com/9cc35541f2e6875f140e0725f8d0ca18.jpg"],"date":"20161011","display_date":"10 月 11 日","id":8874639,"title":"小事 · 爸妈反对我嫁他"},{"images":["http://pic4.zhimg.com/9cf09b1123933b16a135d5a0efa57a97.jpg"],"date":"20161010","display_date":"10 月 10 日","id":8725019,"title":"小事 · 两个小时，赚了七千块"},{"images":["http://pic4.zhimg.com/4854ca8aa59dff0eb52cb7daf0aa03b3.jpg"],"date":"20161009","display_date":"10 月 9 日","id":8868930,"title":"小事 · 不许碰，全都不许碰"},{"images":["http://pic1.zhimg.com/f81eb8e43a4fa33b7be7b7cf66f2d8f4.jpg"],"date":"20161008","display_date":"10 月 8 日","id":8854384,"title":"小事 · 进了拘留所"},{"images":["http://pic1.zhimg.com/06034214e13cecce0fc8b5a6943b5304.jpg"],"date":"20161007","display_date":"10 月 7 日","id":8855382,"title":"小事 · 作为演员拍吻戏"},{"images":["http://pic3.zhimg.com/5c23fef24488b4e72ea034f4246427ba.jpg"],"date":"20161006","display_date":"10 月 6 日","id":8857875,"title":"小事 · 如果没有那碗面"},{"images":["http://pic4.zhimg.com/76db68751ae1ff3add328ff9089c4c43.jpg"],"date":"20161005","display_date":"10 月 5 日","id":8854115,"title":"小事 · 亲手持枪对峙歹徒"}]
     * name : 小事
     */

    private int timestamp;
    private String name;
    /**
     * images : ["http://pic2.zhimg.com/ae852b938565b7c7e10a4e896d3d260d.jpg"]
     * date : 20161024
     * display_date : 10 月 24 日
     * id : 8916065
     * title : 小事 · 不如我们重新来过
     */

    private List<StoriesBean> stories;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

}
