package com.yhzc.schooldormitorymobile.ui.askLeaveDetails;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 19:29
 * @描述:
 */
public class LeaveDetailsModel {

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ApproveListBean> approveList;
        private String textContent;
        private String vcAttachmentPath;
        private List<ContentListBean> contentList;
        private List<DetailListBean> detailList;

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public String getVcAttachmentPath() {
            return vcAttachmentPath;
        }

        public void setVcAttachmentPath(String vcAttachmentPath) {
            this.vcAttachmentPath = vcAttachmentPath;
        }

        public List<ApproveListBean> getApproveList() {
            return approveList;
        }

        public void setApproveList(List<ApproveListBean> approveList) {
            this.approveList = approveList;
        }

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public List<ContentListBean> getContentList() {
            return contentList;
        }

        public void setContentList(List<ContentListBean> contentList) {
            this.contentList = contentList;
        }

        public static class ApproveListBean {
            private String approveType;
            private List<ApproveUserBean> approveUser;

            public String getApproveType() {
                return approveType;
            }

            public void setApproveType(String approveType) {
                this.approveType = approveType;
            }

            public List<ApproveUserBean> getApproveUser() {
                return approveUser;
            }

            public void setApproveUser(List<ApproveUserBean> approveUser) {
                this.approveUser = approveUser;
            }

            public static class ApproveUserBean {
                private String name;
                private String headPic;
                private String opinion;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public String getOpinion() {
                    return opinion;
                }

                public void setOpinion(String opinion) {
                    this.opinion = opinion;
                }
            }
        }

        public static class ContentListBean {
            private String title;
            private String content;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class DetailListBean {

            private String textMark;
            private String vcMaterialCode;
            private String vcUnit;
            private String intNum;
            private String vcSpecification;
            private String vcMaterialName;

            public String getTextMark() {
                return textMark;
            }

            public void setTextMark(String textMark) {
                this.textMark = textMark;
            }

            public String getVcMaterialCode() {
                return vcMaterialCode;
            }

            public void setVcMaterialCode(String vcMaterialCode) {
                this.vcMaterialCode = vcMaterialCode;
            }

            public String getVcUnit() {
                return vcUnit;
            }

            public void setVcUnit(String vcUnit) {
                this.vcUnit = vcUnit;
            }

            public String getIntNum() {
                return intNum;
            }

            public void setIntNum(String intNum) {
                this.intNum = intNum;
            }

            public String getVcSpecification() {
                return vcSpecification;
            }

            public void setVcSpecification(String vcSpecification) {
                this.vcSpecification = vcSpecification;
            }

            public String getVcMaterialName() {
                return vcMaterialName;
            }

            public void setVcMaterialName(String vcMaterialName) {
                this.vcMaterialName = vcMaterialName;
            }
        }
    }
}
