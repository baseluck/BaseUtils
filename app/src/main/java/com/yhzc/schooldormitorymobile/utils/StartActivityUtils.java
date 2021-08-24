package com.yhzc.schooldormitorymobile.utils;

import android.app.Activity;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.ui.InSchool.InSchoolActivity;
import com.yhzc.schooldormitorymobile.ui.approveList.ApproveListActivity;
import com.yhzc.schooldormitorymobile.ui.askLeave.AskLeaveActivity;
import com.yhzc.schooldormitorymobile.ui.backbedroom.checkin.CheckInBedroomActivity;
import com.yhzc.schooldormitorymobile.ui.backbedroom.send.BackBedroomActivity;
import com.yhzc.schooldormitorymobile.ui.checkhealth.list.HealthTaskListActivity;
import com.yhzc.schooldormitorymobile.ui.checkthebed.tasklist.TaskListActivity;
import com.yhzc.schooldormitorymobile.ui.discipline.bzr.list.CkDisciplineListActivity;
import com.yhzc.schooldormitorymobile.ui.discipline.list.DisciplineListActivity;
import com.yhzc.schooldormitorymobile.ui.meeting.initiatelist.MeetingInitiateListActivity;
import com.yhzc.schooldormitorymobile.ui.meeting.list.MeetingListActivity;
import com.yhzc.schooldormitorymobile.ui.message.list.MessageListActivity;
import com.yhzc.schooldormitorymobile.ui.modifystudent.classlist.ClassListActivity;
import com.yhzc.schooldormitorymobile.ui.notetake.list.NoteTakeListActivity;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.list.NoticeListActivity;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newList.NoticeNewListActivity;
import com.yhzc.schooldormitorymobile.ui.outSchool.OutSchoolActivity;
import com.yhzc.schooldormitorymobile.ui.outsider.Outsider2Activity;
import com.yhzc.schooldormitorymobile.ui.performance.PerformanceActivity;
import com.yhzc.schooldormitorymobile.ui.performance.PerformanceRankListActivity;
import com.yhzc.schooldormitorymobile.ui.signIn.SignInActivity;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.applylist.SuppliesApplyListActivity;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.ckqrlist.CkqrApplyListActivity;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.putlist.PutApplyListActivity;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.returnslist.ReturnsListActivity;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.rkqrlist.RkqrApplyListActivity;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/2 15:07
 * @描述:
 */
public class StartActivityUtils {

    public static void startActivity(Activity activity, String type) {
        switch (type) {
            case "rcgl_qddk":
                ActivityUtils.startActivity(SignInActivity.class);
                break;
            case "rcgl_hqtj":
                ActivityUtils.startActivity(BackBedroomActivity.class);
                break;
            case "rcgl_hqdj":
                ActivityUtils.startActivity(CheckInBedroomActivity.class);
                break;
            case "rcgl_tzgg":
                ActivityUtils.startActivity(NoticeListActivity.class);
                break;
            case "rcgl_qjsq":
                ActivityUtils.startActivity(AskLeaveActivity.class);
                break;
            case "rcgl_fbtz":
                ActivityUtils.startActivity(NoticeNewListActivity.class);
                break;
            case "rcgl_spcl":
                ActivityUtils.startActivity(ApproveListActivity.class);
                break;
            case "xxgl_wlry":
                ActivityUtils.startActivity(Outsider2Activity.class);
                break;
            case "xxgl_xscq":
                ActivityUtils.startActivity(OutSchoolActivity.class);
                break;
            case "xxgl_jxdf":
                ActivityUtils.startActivity(PerformanceActivity.class);
                break;
            case "xxgl_jxcj":
                ActivityUtils.startActivity(PerformanceRankListActivity.class);
                break;
            case "xxgl_xsxx":
                ActivityUtils.startActivity(ClassListActivity.class);
                break;
            case "xxgl_xswj":
                ActivityUtils.startActivity(DisciplineListActivity.class);
                break;
            case "xxgl_wjsj_bzr":
                Intent intentWjBzr = new Intent(activity, CkDisciplineListActivity.class);
                intentWjBzr.putExtra("type", "1");
                ActivityUtils.startActivity(intentWjBzr);
                break;
            case "xxgl_wjsj_ld":
                Intent intentWjXld = new Intent(activity, CkDisciplineListActivity.class);
                intentWjXld.putExtra("type", "2");
                ActivityUtils.startActivity(intentWjXld);
                break;
            case "rcgl_ssj":
                ActivityUtils.startActivity(NoteTakeListActivity.class);
                break;
            case "yygl_zscq":
                Intent intentAm = new Intent(activity, TaskListActivity.class);
                intentAm.putExtra("type", "1");
                ActivityUtils.startActivity(intentAm);
                break;
            case "yygl_wscq":
                Intent intentPm = new Intent(activity, TaskListActivity.class);
                intentPm.putExtra("type", "2");
                ActivityUtils.startActivity(intentPm);
            case "yygl_zwcq":
                Intent intentZw = new Intent(activity, TaskListActivity.class);
                intentZw.putExtra("type", "3");
                ActivityUtils.startActivity(intentZw);
                break;
            case "xxgl_xsrx":
                ActivityUtils.startActivity(InSchoolActivity.class);
                break;
            case "hygl_wdhy":
                ActivityUtils.startActivity(MeetingListActivity.class);
                break;
            case "hygl_fbhy":
                ActivityUtils.startActivity(MeetingInitiateListActivity.class);
                break;
            case "rcgl_qksq":
                ActivityUtils.startActivity(PutApplyListActivity.class);
                break;
            case "rcgl_wzsq":
                ActivityUtils.startActivity(SuppliesApplyListActivity.class);
                break;
            case "rcgl_rkqr":
                ActivityUtils.startActivity(RkqrApplyListActivity.class);
                break;
            case "rcgl_ckqr":
                ActivityUtils.startActivity(CkqrApplyListActivity.class);
                break;
            case "rcgl_wztk":
                ActivityUtils.startActivity(ReturnsListActivity.class);
                break;
            case "rcgl_xxzx":
                ActivityUtils.startActivity(MessageListActivity.class);
                break;
            case "yygl_wsrcjc":
                Intent intentRc = new Intent(activity, HealthTaskListActivity.class);
                intentRc.putExtra("type", "1");
                ActivityUtils.startActivity(intentRc);
                break;
            case "yygl_wstjjc":
                Intent intentTj = new Intent(activity, HealthTaskListActivity.class);
                intentTj.putExtra("type", "2");
                ActivityUtils.startActivity(intentTj);
                break;
            default:
                ToastUtils.showShort("正在开发");
                break;
        }
    }
}
