package com.yhzc.schooldormitorymobile.ui.meeting.signin;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/9 17:29
 * @描述:
 */
public class MeetingUserAdapter extends BaseQuickAdapter<MeetingUserModel.DataBean, BaseViewHolder>
//        implements Filterable
{

//    private List<MeetingUserModel.DataBean> mFilterList = new ArrayList<>();

    public MeetingUserAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MeetingUserModel.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_room_no, dataBean.getVcJoinUserName());
        if (dataBean.getBlSingle() == 1) {
            baseViewHolder.setBackgroundResource(R.id.tv_status, R.drawable.check_bed_choose_bed_normal_left);
        } else {
            baseViewHolder.setBackgroundResource(R.id.tv_status, R.drawable.check_bed_choose_bed_abnormal_left);
        }
        if (dataBean.getBlSignOut() == 1) {
            baseViewHolder.setBackgroundResource(R.id.tv_status2, R.drawable.check_bed_choose_bed_normal_right);
        } else {
            baseViewHolder.setBackgroundResource(R.id.tv_status2, R.drawable.check_bed_choose_bed_abnormal_right);
        }
    }
//
//    @Override
//    public void setList(@Nullable Collection<? extends MeetingUserModel.DataBean> list) {
//        super.setList(list);
//        mFilterList = (List<MeetingUserModel.DataBean>) list;
//
//    }
//
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String name = constraint.toString();
//                if (!StringUtils.isEmpty(name)) {
//                    List<MeetingUserModel.DataBean> data = getData();
//                    List<MeetingUserModel.DataBean> dataBeans = new ArrayList<>();
//                    for (MeetingUserModel.DataBean dataBean : data) {
//                        if (dataBean.getVcJoinUserName().contains(name)) {
//                            dataBeans.add(dataBean);
//                        }
//                    }
//
//                    mFilterList = dataBeans;
//                } else {
//                    mFilterList = getData();
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mFilterList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                mFilterList = (List<MeetingUserModel.DataBean>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
}
