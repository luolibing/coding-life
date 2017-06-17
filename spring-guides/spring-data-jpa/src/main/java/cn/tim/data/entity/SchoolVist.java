package cn.tim.data.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by LuoLiBing on 15/6/17.
 */
@Entity
@Table(name="school_vist")
public class SchoolVist implements Serializable {

    @Id
    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "first_vist_date")
    private Date firstVistDate;

    @Column(name = "last_vist_date")
    private Date lastVistDate;

    @Column(name = "appointment_date")
    private Date appointmentDate;

    @Column(name = "vist_cycle")
    private Integer vistCycle;

    @Column(name = "next_job")
    private String nextJob;

    @Column(name = "problem")
    private String problem;

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }


    public void setFirstVistDate(Date firstVistDate) {
        this.firstVistDate = firstVistDate;
    }


    public void setLastVistDate(Date lastVistDate) {
        this.lastVistDate = lastVistDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Integer getVistCycle() {
        return vistCycle;
    }

    public void setVistCycle(Integer vistCycle) {
        this.vistCycle = vistCycle;
    }

    public String getNextJob() {
        return nextJob;
    }

    public void setNextJob(String nextJob) {
        this.nextJob = nextJob;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Date getFirstVistDate() {
        return firstVistDate;
    }

    public Date getLastVistDate() {
        return lastVistDate;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }
}
