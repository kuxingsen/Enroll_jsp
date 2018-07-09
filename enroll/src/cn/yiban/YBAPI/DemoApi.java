package cn.yiban.YBAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * s所有易班测试权限接口
 * Created by 傻逼 on 2018/1/24.
 */
public class DemoApi {
    private final String TEXT_ENCODING = "UTF-8";

    private static final String YIBAN_OPEN_URL = "https://openapi.yiban.cn/";

    /**
     * 以下为 用户接口
     */
    private static final String YIBAN_USER_ME_INFO	= "user/me";
    private static final String YIBAN_USER_OTHER	= "user/other";
    private static final String YIBAN_USER_ISREAL	= "user/is_real";
    //查看本人基本消息，不包含实名信息
    public String me(String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_ME_INFO;
        query += "?access_token=";
        query += access_token;
        return HTTPSimple.GET(query);
    }
    //查看指定用户基本信息，不包含实名信息
    public String other(int userid , String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_OTHER;
        query += "?access_token=";
        query += access_token;
        query += "&yb_userid=";
        query += String.valueOf(userid);
        return HTTPSimple.GET(query);
    }
    //查看指定用户是否实名认证
    public String is_real(int yb_userid , String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_ISREAL;
        query += "?access_token=";
        query += access_token;
        query += "&yb_userid=";
        query += String.valueOf(yb_userid);
        return HTTPSimple.GET(query);
    }

    /**
     * 以下为 好友分组接口
     */
    private static final String API_FRIEND_APPLY = "friend/apply";
    private static final String API_FRIEND_LIST	= "friend/me_list";
    private static final String API_FRIEND_RECOMMEND = "friend/recommend";
    private static final String API_FRIEND_CHECK= "friend/check";
    private static final String API_FRIEND_REMOVE= "friend/remove";
    //查看好友列表
    public String list(int page, int count, String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += API_FRIEND_LIST;
        query += "?access_token=";
        query += access_token;
        query += "&page=";
        query += String.valueOf(page);
        query += "&count=";
        query += String.valueOf(count);
        return HTTPSimple.GET(query);
    }
    //查询与指定用户的好友关系
    public String check(int yb_friend_uid , String access_token)
    {
        String query = YIBAN_OPEN_URL;
        query += API_FRIEND_CHECK;
        query += "?access_token=";
        query += access_token;
        query += "&yb_friend_uid=";
        query += String.valueOf(yb_friend_uid);
        return HTTPSimple.GET(query);
    }
    //推荐好友接口
    public static String recommend(String access_token,int count)
    {
        String query = YIBAN_OPEN_URL+API_FRIEND_RECOMMEND;
        query += "?access_token=";
        query += access_token;
        query += "&count=";
        query += count;
        //System.out.println(query);
        return HTTPSimple.GET(query);
    }
    //发送好友申请
    public static String apply(String access_token,String to_yb_uid,String content)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("to_yb_uid", to_yb_uid));
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("content", content));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_FRIEND_APPLY, param);
    }
    //删除指定好友
    public static String remove(String access_token,String yb_friend_uid)
    {
        String query = YIBAN_OPEN_URL+API_FRIEND_REMOVE;
        query += "?access_token=";
        query += access_token;
        query += "&yb_friend_uid=";
        query += yb_friend_uid;
        return HTTPSimple.GET(query);
    }

    /**
     * 以下为 消息接口
     */
    private static final String API_MSG_LETTER = "msg/letter";
    //发送消息接口
    public static String letter(String access_token,String to_yb_uid,String content,String template)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("to_yb_uid", to_yb_uid));
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("content", content));
        param.add(new BasicNameValuePair("template", template));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_MSG_LETTER, param);
    }
    /**
     * 以下为 资讯服务接口
     */
    private static final String API_NEWS_PUSH = "news/yb_push";
    //资讯推送接口
    public static String ybPush(String access_token,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_NEWS_PUSH+url);
    }

    /**
     * 以下为 校级接口
     */
    private static final String API_SCHOOL_RELATEAPP = "school/relate_app";
    //获取当前应用所属开发者/可见学校其它的关联应用。
    public static String relateApp(String access_token,String sc_name,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        if(!Objects.equals(sc_name, ""))
        {
            url += "&sc_name=";
            url += sc_name;
        }
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_SCHOOL_RELATEAPP+url);
    }

    /**
     * 以下为群话题接口
     */
    private static final String API_GROUP_PUBLICGROUP = "group/public_group";
    private static final String API_GROUP_ORGANGROUP = "group/organ_group";
    private static final String API_GROUP_MYGROUP = "group/my_group";
    private static final String API_GROUP_GROUPINFO = "group/group_info";
    private static final String API_GROUP_GROUPMEMBER = "group/group_member";
    private static final String API_GROUP_GROUPTOPIC = "group/group_topic";
    private static final String API_GROUP_ORGANTOPIC = "group/organ_topic";
    private static final String API_GROUP_MYTOPIC = "group/my_topic";
    private static final String API_GROUP_HOTTOPIC = "group/hot_topic";
    private static final String API_GROUP_TOPICINFO = "group/topic_info";
    private static final String API_GROUP_TOPICCOMMENT = "group/topic_comment";
    private static final String API_GROUP_SENDTOPIC = "group/send_topic";
    private static final String API_GROUP_SENDCOMMENT = "group/send_comment";
    private static final String API_GROUP_DELETETOPIC = "group/delete_topic";
    private static final String API_GROUP_DELETECOMMENT = "group/delete_comment";
    //获取当前用户已加入的公共群。
    public static String publicGroup(String access_token,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_PUBLICGROUP+url);
    }
    //获取当前用户已加入的机构群
    public static String organGroup(String access_token,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_ORGANGROUP+url);
    }
    //获取当前用户创建的机构群/公共群
    public static String myGroup(String access_token,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_MYGROUP+url);
    }
    //获取指定机构群/公共群信息
    public static String groupInfo(String access_token,String group_id)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_GROUPINFO+url);
    }
    //获取指定机构群/公共群成员列表
    public static String groupMember(String access_token,String group_id,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_GROUPMEMBER+url);
    }
    //获取指定机构群/公共群话题列表
    public static String groupTopic(String access_token,String group_id,int page,int count,int order)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        url += "&order=";
        url += order;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_GROUPTOPIC+url);
    }
    //获取指定机构号板块话题列表
    public static String organTopic(String access_token,String organ_uid,int page,int count,String item,int order)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += organ_uid;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        if(!item.equals(""))
        {
            url += "&item=";
            url += item;
        }
        url += "&order=";
        url += order;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_ORGANTOPIC+url);
    }
    //获取当前用户发表的话题列表
    public static String myTopic(String access_token,String group_id,int page,int count,int order)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        url += "&order=";
        url += order;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_MYTOPIC+url);
    }
    //获取全站/机构号热门话题列表
    public static String hotTopic(String access_token,int page,int count,int order)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        url += "&order=";
        url += order;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_HOTTOPIC+url);
    }
    //获取指定话题内容
    public static String topicInfo(String access_token,String group_id,String organ_id,String topic_id)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&organ_id=";
        url += organ_id;
        url += "&topic_id=";
        url += topic_id;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_TOPICINFO+url);
    }
    //获取指定话题的评论列表
    public static String topicComment(String access_token,String group_id,String organ_id,String topic_id,int page,int count)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&organ_id=";
        url += organ_id;
        url += "&topic_id=";
        url += topic_id;
        url += "&page=";
        url += page;
        url += "&count=";
        url += count;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_TOPICCOMMENT+url);
    }
    //在指定机构群/公共群范围发表话题
    public static String SendTopic(String access_token,String group_id,String topic_title,String topic_content)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("group_id", group_id));
        param.add(new BasicNameValuePair("topic_title", topic_title));
        param.add(new BasicNameValuePair("topic_content", topic_content));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_GROUP_SENDTOPIC, param);
    }
    //对指定话题发表/回复评论
    public static String sendComment(String access_token,String group_id,String organ_id,String topic_id,String comment_content,String comment_id)
    {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("access_token", access_token));
        param.add(new BasicNameValuePair("topic_id", topic_id));
        param.add(new BasicNameValuePair("comment_content", comment_content));
        if(group_id.equals(""))
            param.add(new BasicNameValuePair("group_id", group_id));
        if(organ_id.equals(""))
            param.add(new BasicNameValuePair("organ_id", organ_id));
        if(comment_id.equals(""))
            param.add(new BasicNameValuePair("comment_id", comment_id));
        return HTTPSimple.POST(YIBAN_OPEN_URL + API_GROUP_SENDCOMMENT, param);
    }
    //删除当前用户指定机构群/公共群范围发表的话题
    public static String deleteTopic(String access_token,String group_id,String topic_id)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&topic_id=";
        url += topic_id;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_DELETETOPIC+url);
    }
    //删除当前用户发表的话题评论。
    public static String deleteComment(String access_token,String group_id,String organ_id,String topic_id,String comment_id)
    {
        String url = "?access_token=";
        url += access_token;
        url += "&group_id=";
        url += group_id;
        url += "&organ_id=";
        url += organ_id;
        url += "&topic_id=";
        url += topic_id;
        url += "&comment_id=";
        url += comment_id;
        return HTTPSimple.GET(YIBAN_OPEN_URL+API_GROUP_DELETECOMMENT+url);
    }
}
