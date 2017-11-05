package server.jack.com;

/**
 * @author jack
 * @ClassName: Process.class
 * @Description: 进程体
 * @Date 2017年10月13日 上午10:14:23
 */
public class Process {
    /**
     * 进程标号
     */
    private int processId;
    /**
     * 进程名称
     */
    private String processName;
    /**
     * 就绪时间
     */
    private long readyTime;
    /**
     * 服务时间
     */
    private long serverTime;

    /**
     * @param processId
     * @param processName
     * @param readyTime
     * @param serverTime
     */
    public Process(int processId, String processName, long readyTime, long serverTime) {
        super();
        this.processId = processId;
        this.processName = processName;
        this.readyTime = readyTime;
        this.serverTime = serverTime;
    }

    /**
     * @return the processId
     */
    public int getProcessId() {
        return processId;
    }

    /**
     * @return the processName
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * @return the readyTime
     */
    public long getReadyTime() {
        return readyTime;
    }

    /**
     * @return the serverTime
     */
    public long getServerTime() {
        return serverTime;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + processId;
        result = prime * result + ((processName == null) ? 0 : processName.hashCode());
        result = prime * result + (int) (readyTime ^ (readyTime >>> 32));
        result = prime * result + (int) (serverTime ^ (serverTime >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Process other = (Process) obj;
        if (processId != other.processId)
            return false;
        if (processName == null) {
            if (other.processName != null)
                return false;
        } else if (!processName.equals(other.processName))
            return false;
        if (readyTime != other.readyTime)
            return false;
        if (serverTime != other.serverTime)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Process [processId=" + processId + ", processName=" + processName + ", readyTime=" + readyTime
                + ", serverTime=" + serverTime + "]";
    }

}
