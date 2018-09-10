package com.adshow.player.bean;

import com.adshow.player.dao.AdvertisingDao;
import com.adshow.player.dao.DaoSession;
import com.adshow.player.dao.HistoryDao;
import com.adshow.player.dao.ScheduleDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Date;
import java.util.List;

@Entity
public class Advertising {
    @Id
    private String id;
    private String name;
    private String description;
    private String url;
    private Date downloadTime;
    private Date validateTime;
    private Date unzipTime;

    @ToMany(referencedJoinProperty = "advertisingId")
    private List<History> histories;
    @ToMany(referencedJoinProperty = "advertisingId")
    private List<Schedule> schedules;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1884009259)
    private transient AdvertisingDao myDao;

    @Generated(hash = 10497530)
    public Advertising(String id, String name, String description, String url,
            Date downloadTime, Date validateTime, Date unzipTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.downloadTime = downloadTime;
        this.validateTime = validateTime;
        this.unzipTime = unzipTime;
    }

    @Generated(hash = 1927124881)
    public Advertising() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }

    public Date getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(Date validateTime) {
        this.validateTime = validateTime;
    }

    public Date getUnzipTime() {
        return unzipTime;
    }

    public void setUnzipTime(Date unzipTime) {
        this.unzipTime = unzipTime;
    }

    @Override
    public String toString() {
        return "Advertising{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", downloadTime=" + downloadTime +
                ", validateTime=" + validateTime +
                ", unzipTime=" + unzipTime +
                ", histories=" + histories +
                ", schedules=" + schedules +
                '}';
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1951144631)
    public List<History> getHistories() {
        if (histories == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            HistoryDao targetDao = daoSession.getHistoryDao();
            List<History> historiesNew = targetDao._queryAdvertising_Histories(id);
            synchronized (this) {
                if (histories == null) {
                    histories = historiesNew;
                }
            }
        }
        return histories;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 36099026)
    public synchronized void resetHistories() {
        histories = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 436359)
    public List<Schedule> getSchedules() {
        if (schedules == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ScheduleDao targetDao = daoSession.getScheduleDao();
            List<Schedule> schedulesNew = targetDao._queryAdvertising_Schedules(id);
            synchronized (this) {
                if (schedules == null) {
                    schedules = schedulesNew;
                }
            }
        }
        return schedules;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 283382071)
    public synchronized void resetSchedules() {
        schedules = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1680467244)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAdvertisingDao() : null;
    }
}
