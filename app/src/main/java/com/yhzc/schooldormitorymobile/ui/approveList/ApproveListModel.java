package com.yhzc.schooldormitorymobile.ui.approveList;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 17:06
 * @描述:
 */
public class ApproveListModel {

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
        private List<ListBean> list;
        private PaginationBean pagination;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public static class PaginationBean {
            private int currentPage;
            private int pageSize;
            private int total;

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }

        public static class ListBean {
            private String vcTitle;
            private String taskId;
            private String processInstanceKey;
            private String processInstanceName;
            private String processInstanceId;
            private String name;
            private String assignee;
            private String executionId;
            private String processDefinitionId;
            private int suspensionState;
            private String createTime;
            private String mainId;
            private int priority;
            private String initiator;

            public String getMainId() {
                return mainId;
            }

            public void setMainId(String mainId) {
                this.mainId = mainId;
            }

            public String getProcessInstanceName() {
                return processInstanceName;
            }

            public void setProcessInstanceName(String processInstanceName) {
                this.processInstanceName = processInstanceName;
            }

            public String getVcTitle() {
                return vcTitle;
            }

            public void setVcTitle(String vcTitle) {
                this.vcTitle = vcTitle;
            }

            public String getTaskId() {
                return taskId;
            }

            public void setTaskId(String taskId) {
                this.taskId = taskId;
            }

            public String getProcessInstanceKey() {
                return processInstanceKey;
            }

            public void setProcessInstanceKey(String processInstanceKey) {
                this.processInstanceKey = processInstanceKey;
            }

            public String getProcessInstanceId() {
                return processInstanceId;
            }

            public void setProcessInstanceId(String processInstanceId) {
                this.processInstanceId = processInstanceId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAssignee() {
                return assignee;
            }

            public void setAssignee(String assignee) {
                this.assignee = assignee;
            }

            public String getExecutionId() {
                return executionId;
            }

            public void setExecutionId(String executionId) {
                this.executionId = executionId;
            }

            public String getProcessDefinitionId() {
                return processDefinitionId;
            }

            public void setProcessDefinitionId(String processDefinitionId) {
                this.processDefinitionId = processDefinitionId;
            }

            public int getSuspensionState() {
                return suspensionState;
            }

            public void setSuspensionState(int suspensionState) {
                this.suspensionState = suspensionState;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public String getInitiator() {
                return initiator;
            }

            public void setInitiator(String initiator) {
                this.initiator = initiator;
            }
        }
    }
}
