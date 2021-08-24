package com.yhzc.schooldormitorymobile.http;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/19 14:18
 * @描述:
 */
public class ApiUrl {

    public static final int SUCCESS = 200;

    public static final int OUT = 601;

    /**
     * 正式服务器
     */
//    public static final String BASE_URL = "http://124.71.235.115";
    /**
     * 测试服务器
     */
    public static final String BASE_URL = "http://39.99.169.115:7771";
//    public static final String BASE_URL = "http://" + Cache.getBaseHttp() + "/";
//    public static final String BASE_URL = "http://ycy1991.natapp4.cc/";
    /**
     * 登录
     */
    public static final String LOGIN = BASE_URL + "/api/app/Login/Login";
    /**
     * 验证token
     */
    public static final String CHECKTOKEN = BASE_URL + "/api/app/Login/checkLogin";
    /**
     * 获取所有的新闻信息
     */
    public static final String CONTENTLIST = BASE_URL + "/api/app/mainContent/contentList";
    /**
     * 获取所有的指定版位的广告
     */
    public static final String ADLIST = BASE_URL + "/api/app/mainContent/adList";
    public static final String CLASSSTU = BASE_URL + "/api/app/school/getClassStu";
    /**
     * 获取所有的已经发布的通知公告信息
     */
    public static final String NOTICELIST = BASE_URL + "/api/app/mainContent/noticeList";
    /**
     * 内容详情
     */
    public static final String CONTENT = BASE_URL + "/api/app/mainContent/content/";
    /**
     * 打卡范围及时间获取
     */
    public static final String SIGNLOCATION = BASE_URL + "/api/app/oa/attendance/getdkfw";
    /**
     * 打卡
     */
    public static final String DK = BASE_URL + "/api/app/oa/attendance/dk";
    /**
     * 外勤打卡
     */
    public static final String WQDK = BASE_URL + "/api/app/oa/attendance/wqdk";
    /**
     * 修改密码
     */
    public static final String CHANGEPWD = BASE_URL + "/api/app/userCenter/Actions/ModifyPassword";
    /**
     * 上传头像/人脸认证图
     */
    public static final String UPFACEPIC = BASE_URL + "/api/app/userCenter/Uploader/userAvatar";
    /**
     * 上传打卡人脸
     */
    public static final String UPFACE = BASE_URL + "/api/app/userCenter/Uploader/kqzp";
    /**
     * 打卡记录
     */
    public static final String SIGNLIST = BASE_URL + "/api/app/oa/attendance/dk/record";
    /**
     * 修改联系方式
     */
    public static final String UPUSER = BASE_URL + "/api/app/userCenter/updateUser";
    /**
     * 通知消息列表
     */
    public static final String NOTICEMSGLIST = BASE_URL + "/api/app/notice/action/getNoticeList";
    /**
     * 我发布得通知
     */
    public static final String MYNOTICELIST = BASE_URL + "/api/app/notice/action/mylist";
    /**
     * 通知消息详情
     */
    public static final String NOTICEINFO = BASE_URL + "/api/app/notice/action/getInfo";
    /**
     * 回复
     */
    public static final String REPLY = BASE_URL + "/api/app/notice/action/reply";
    /**
     * 获取未读消息
     */
    public static final String NEWNOTICE = BASE_URL + "/api/app/notice/action/getNoRead";
    /**
     * 获取用户列表-发布通知
     */
    public static final String SELECTUSER = BASE_URL + "/api/Permission/Users/Selector";
    /**
     * 上传图片视频
     */
    public static final String UPLOADIMG = BASE_URL + "/api/Common/Uploaders/annexpic";
    public static final String UPLOAD = BASE_URL + "/fileManager/upload";
    public static final String UPLOADSP = BASE_URL + "/api/app/school/updStuPhtot";
    public static final String GETSEX = BASE_URL + "/api/System/DictionaryData/963255a34ea64a2584c5d1ba269c1fe6/Data/Selector";
    public static final String GETMZ = BASE_URL + "/api/System/DictionaryData/b6cd65a763fa45eb9fe98e5057693e40/Data/Selector";
    public static final String GETDQ = BASE_URL + "/api/System/Province/";
    public static final String SELECTOR = BASE_URL + "/Selector";
    /**
     * 发布通知公告
     */
    public static final String UPDATENOTICE = BASE_URL + "/api/app/notice/action/create";
    /**
     * 请假类型
     */
    public static final String LEAVETYPE = BASE_URL + "/api/System/DictionaryData/c4a8224e4da6495bb37654db93db6467/Data/Selector";
    /**
     * 请假-选择班级列表
     */
    public static final String LEAVECLASSLIST = BASE_URL + "/api/school/SchoolGradeClass/action/getTeacherGradeClass";
    /**
     * 请假-选择学生列表
     */
    public static final String LEAVESTUDENTLIST = BASE_URL + "/api/school/SchoolStudent/action/getStuList";
    /**
     * 请假审批流程
     */
    public static final String LEAVEAPPROVER = BASE_URL + "/api/app/oa/work/studentVacation/approver";
    /**
     * 请假申请
     */
    public static final String LEAVESEND = BASE_URL + "/api/app/oa/work/action/create";
    /**
     * 我的请假列表
     */
    public static final String LEAVEMYLIST = BASE_URL + "/api/app/oa/work/vacation/me/";
    /**
     * 待处理任务列表
     */
    public static final String APPROVELIST = BASE_URL + "/api/activiti/task/list";
    /**
     * 请假详情
     */
//    public static final String LEAVEDETAILS ="/api/app/oa/work/studentVacation/";
    /**
     * 请假详情
     */
    public static final String LEAVEDETAILS = BASE_URL + "/api/app/oa/work/";
    /**
     * 我的任务-审批
     */
    public static final String LEAVEAPPROVE = BASE_URL + "/api/activiti/task/action/complete";
    /**
     * 我的任务-委派
     */
    public static final String LEAVEASSIGNED = BASE_URL + "/api/activiti/task/action/assigned";
    /**
     * 我的考核列表
     */
    public static final String MYPLANLIST = BASE_URL + "/api/app/examinePlan/myplan";
    /**
     * 全部考核列表
     */
    public static final String ALLPLANLIST = BASE_URL + "/api/app/examinePlan/list";
    /**
     * 考核成绩列表
     */
    public static final String PERRANKDETAILS = BASE_URL + "/api/app/examinePlan/rank";
    /**
     * 考核详情
     */
    public static final String MYPLANDETAILS = BASE_URL + "/api/app/examinePlan/getPlanFillInfo";
    /**
     * 提交考核
     */
    public static final String UPDATEPLAN = BASE_URL + "/api/app/examinePlan/fillResult";
    /**
     * 外来人员登记
     */
    public static final String OUTSIDER = BASE_URL + "/api/app/enterSchoolRegister/dj/2";
    /**
     * 外来人员登记记录
     */
    public static final String OUTSIDERRECORD = BASE_URL + "/api/app/enterSchoolRegister/record/2";
    /**
     * 根据学生卡号获取请假详情
     */
    public static final String STUDENTLEAVEINFO = BASE_URL + "/api/app/oa/work/studentVacation/info";
    /**
     * 根据学生卡号获取学生信息
     */
    public static final String STUDENTINFO = BASE_URL + "/api/app/oa/work/student/info";
    public static final String STUDENTINFO2 = BASE_URL + "/api/app/school/student/info/";
    /**
     * 学生返校登记
     */
    public static final String STUDENTIN = BASE_URL + "/api/app/enterSchoolRegister/dj/1";
    /**
     * 学生返校记录
     */
    public static final String STUDENTINRECORD = BASE_URL + "/api/app/enterSchoolRegister/record/1";
    /**
     * 查寝任务列表
     */
    public static final String CHECKBEDTASKLIST = BASE_URL + "/api/app/dormCheckPerson/task/page";
    /**
     * 获取寝室列表
     */
    public static final String CHECKBEDROOMLIST = BASE_URL + "/api/app/dormCheckPerson/task/";
    public static final String CHECKBEDROOMLIST2 = BASE_URL + "/api/app/dormCheckPerson/task/record/";
    /**
     * 获取学生列表
     */
    public static final String CHECKBEDROOMSTUDENTLIST = BASE_URL + "/api/app/dormCheckPerson/record2/";
    /**
     * 寝室检查结果提交
     */
    public static final String CHECKBEDROOMSUBMIT = BASE_URL + "/api/app/dormCheckPerson/submit";
    /**
     * 会议列表
     */
    public static final String MEETINGLIST = BASE_URL + "/api/app/meeting/list/me";
    /**
     * 会议发起
     */
    public static final String MEETINGINITIATE = BASE_URL + "/api/app/meeting/create";
    /**
     * 会议签到人员
     */
    public static final String MEETINGUSERLIST = BASE_URL + "/api/oa/OaMeeting/record";
    /**
     * 会议签到、签退
     */
    public static final String MEETINGSIGN = BASE_URL + "/api/app/meeting/sign";
    /**
     * 会议删除
     */
    public static final String MEETINGDELETE = BASE_URL + "/api/app/meeting/";
    /**
     * 会议发布
     */
    public static final String MEETINGPUBLISH = BASE_URL + "/api/app/meeting/send/";
    /**
     * 会议详情
     */
    public static final String MEETINGDETAILS = BASE_URL + "/api/app/meeting/";
    /**
     * 会议修改
     */
    public static final String MEETINGPUT = BASE_URL + "/api/app/meeting/";
    /**
     * 卫生检查任务列表
     */
    public static final String CHECKHEALTHTASKLIST = BASE_URL + "/api/app/dormCheckHygiene/task/page";
    /**
     * 卫生检查寝室列表
     */
    public static final String CHECKHEALTHROOMLIST = BASE_URL + "/api/app/dormCheckHygiene/task/";
    public static final String CHECKHEALTHROOMLIST2 = BASE_URL + "/api/app/dormCheckHygiene/task/record/";
    /**
     * 卫生检查详情
     */
    public static final String CHECKHEALTHROOMDETAILS = BASE_URL + "/api/app/dormCheckHygiene/record2/";
    /**
     * 提交卫生检查结果
     */
    public static final String CHECKHEALTHSUBMIT = BASE_URL + "/api/app/dormCheckHygiene/submit";
    /**
     * 分类列表  -1-全部(默认)  0-自定义用户类别   1-系统类别
     */
    public static final String NOTECATELIST = BASE_URL + "/api/app/tool/memo/cateList";
    /**
     * 创建自定义分类
     */
    public static final String NOTECATECREATE = BASE_URL + "/api/app/tool/memo/cate/create";
    /**
     * 编辑自定义分类 put-编辑  delete-删除
     */
    public static final String NOTECATEEDIT = BASE_URL + "/api/app/tool/memo/cate/";
    /**
     * 统计记录
     */
    public static final String NOTELIST = BASE_URL + "/api/app/tool/memo/tj";
    /**
     * 新增记录
     */
    public static final String NOTECREATE = BASE_URL + "/api/app/tool/memo/record/create";
    /**
     * 记录详情
     */
    public static final String NOTEDETAILS = BASE_URL + "/api/app/tool/memo/record/";
    public static final String LOCATIONLIST = BASE_URL + "/api/app/dormCheckHygiene/task/position/";
    public static final String LOCATIONLIST2 = BASE_URL + "/api/app/dormCheckPerson/task/position/";
    /**
     * 消息列表
     */
    public static final String MESSAGELIST = BASE_URL + "/api/System/Message";
    /**
     * 消息未读
     */
    public static final String MESSAGEUNREAD = BASE_URL + "/api/System/Message/unreadCount";
    /**
     * 消息全部已读
     */
    public static final String MESSAGEREADALL = BASE_URL + "/api/System/Message/Actions/ReadAll";
    /**
     * 消息详情
     */
    public static final String MESSAGEDETAILS = BASE_URL + "/api/System/Message/ReadInfo/";
    /**
     * 物资库存列表
     */
    public static final String WZKCLIST = BASE_URL + "/api/school/stock/list";
    /**
     * 物资退货提交
     */
    public static final String WZKCTH = BASE_URL + "/api/school/stock/th";
    /**
     * 入库出库确认
     */
    public static final String RKCKQR = BASE_URL + "/api/school/stock/updStock";
    /**
     * 物资退货列表
     */
    public static final String WZKCTHLIST = BASE_URL + "/api/school/stock/th/me";
    /**
     * 库存操作记录
     */
    public static final String KCCZLIST = BASE_URL + "/api/school/stock/record";
    /**
     * 物资退货详情
     */
    public static final String WZKCTHDETAILS = BASE_URL + "/api/school/stock/record/";
    /**
     * 违纪列表
     */
    public static final String WJLIST = BASE_URL + "/api/app/school/SchoolViolation/page";
    /**
     * 班主任查看违纪
     */
    public static final String BZRLIST = BASE_URL + "/api/app/school/SchoolViolation/headTeacher/page";
    /**
     * 校领导查看违纪
     */
    public static final String XLDLIST = BASE_URL + "/api/app/school/SchoolViolation/leader/page";

    /**
     * 违纪详情
     */
    public static final String WJDETAILS = BASE_URL + "/api/app/school/SchoolViolation/";
    /**
     * 违纪级别
     */
    public static final String WJLEVEL = BASE_URL + "/api/school/SchoolViolation/dropList/level";
    /**
     * 学生列表
     */
    public static final String STUDENTLIST = BASE_URL + "/api/app/school/SchoolViolation/dropList/stu";
    /**
     * 新增违纪
     */
    public static final String WJADD = BASE_URL + "/api/app/school/SchoolViolation";
    /**
     * 回寝提交
     */
    public static final String BACKBEDROOMSEND = BASE_URL + "/api/app/oa/backDrom/create";
    /**
     * 回寝列表
     */
    public static final String BACKBEDROOMLIST = BASE_URL + "/api/app/oa/backDrom/list";
    /**
     * 查询学生回寝信息
     */
    public static final String BACKBEDROOMLAST = BASE_URL + "/api/app/oa/backDrom/last";
    /**
     * 宿管更新回寝时间
     */
    public static final String BACKBEDROOMCHECK = BASE_URL + "/api/app/oa/backDrom/";

    public static final String XS = BASE_URL + "/api/app/school/student/info/";
    public static final String GXXS = BASE_URL + "/api/app/school/student/";
    /**
     * 首页常用功能
     */
    public static final String HOMEOFFICEITEM = BASE_URL + "/api/app/Login/usuallyModuleList";
    /**
     * 我的全部功能
     */
    public static final String MOBILEOFFICEITEM = BASE_URL + "/api/app/Login/moduleList";
    /**
     * 更新我的常用功能
     */
    public static final String UPDATEOFFICEITEM = BASE_URL + "/api/app/Login/upUsuallyModule";
    /**
     * 个人考勤统计
     */
    public static final String PERSONSTATISTICS = BASE_URL + "/api/app/dsa/kq/tjByUser";
    /**
     * 本周全部缺勤统计
     */
    public static final String ALLWEEKSTATISTICS = BASE_URL + "/api/app/dsa/kq/qjuser/week";

    /*
     *
     *
     *
     *
     *
     *
     */
    /**************人脸识别所需接口**************/
    /**
     * 百度人脸搜索
     */
    public static final String BAIDUFACE = "https://aip.baidubce.com/rest/2.0/face/v3/search";
    /**
     * 获取facetoken
     */
    public static final String FACETOKEN = BASE_URL + "/api/app/userCenter/getUserFaceToken";
    /**
     * 获取百度人脸认证token
     */
    public static final String BAIDUTOKEN = BASE_URL + "/api/app/userCenter/baiduToken";


}
