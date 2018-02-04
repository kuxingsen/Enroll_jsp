package cn.yiban.YBAPI;

/**
 * Created by ɵ�� on 2018/1/29.
 */
public class User {

    private final String YIBAN_OPEN_URL		= "https://openapi.yiban.cn/";

    private static final String YIBAN_USER_ME_INFO	= "user/me";
    private static final String YIBAN_USER_OTHER	= "user/other";
    private static final String YIBAN_USER_REALME	= "user/real_me";

    /**
     * �Ѿ�ͨ����Ȩ�ķ�������
     */
    private String token;

    /**
     * ���캯��
     *
     * @param	String ��������
     */
    public User(String token)
    {
        this.token = token;
    }

    /**
     * ���˻�����Ϣ
     *
     * ������ʵ����Ϣ��������Ϣ�����˿��Բ鿴
     *
     * @return	String	JSON�ַ���
     */
    public String me()
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_ME_INFO;
        query += "?access_token=";
        query += token;
        return HTTPSimple.GET(query);
    }

    /**
     * �鿴�û���Ϣ
     *
     * ͨ��ָ���û�ID�鿴�����û��Ļ�����Ϣ
     *
     * @param	int		�û�ID
     * @return	String	JSON�ַ���
     */
    public String other(int userid)
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_OTHER;
        query += "?access_token=";
        query += token;
        query += "&yb_userid=";
        query += String.valueOf(userid);
        return HTTPSimple.GET(query);
    }

    /**
     * �Լ���ʵ����Ϣ
     *
     * ֻ�ܲ鿴�Լ���ʵ����Ϣ�����ܲ鿴���˵�
     *
     * @return	String	JSON�ַ���
     */
    public String realme()
    {
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_REALME;
        query += "?access_token=";
        query += token;
        return HTTPSimple.GET(query);
    }

}
