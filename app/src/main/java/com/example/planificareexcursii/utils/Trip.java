package com.example.planificareexcursii.utils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;
import java.io.Serializable;
import java.util.Date;
@Entity(tableName = "trips"
        ,
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "userId",
                childColumns = "id",
                onDelete = CASCADE),indices = @Index("id")
)
public class Trip implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="userId")
    private long userId;
    @ColumnInfo(name="id")
    private long id;

    @ColumnInfo(name="destinatie1")
    private String destinatie1;
    @ColumnInfo(name="destinatie2")
    private String destinatie2;
    @ColumnInfo(name="destinatie3")
    private String destinatie3;
    @ColumnInfo(name="datastart")
    private Date data;
    @ColumnInfo(name="datasfinal")
    private Date dataFinal;

    @Ignore
    public Trip(String destinatie1, String destinatie2, String destinatie3, Date data, Date dataFinal) {
        this.destinatie1 = destinatie1;
        this.destinatie2 = destinatie2;
        this.destinatie3 = destinatie3;
        this.data = data;
        this.dataFinal = dataFinal;
    }

    public Trip(long id, long userId, String destinatie1, String destinatie2, String destinatie3, Date data, Date dataFinal) {
        this.id = id;
        this.userId = userId;
        this.destinatie1 = destinatie1;
        this.destinatie2 = destinatie2;
        this.destinatie3 = destinatie3;
        this.data = data;
        this.dataFinal = dataFinal;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDestinatie1() {
        return destinatie1;
    }

    public void setDestinatie1(String destinatie1) {
        this.destinatie1 = destinatie1;
    }

    public String getDestinatie2() {
        return destinatie2;
    }

    public void setDestinatie2(String destinatie2) {
        this.destinatie2 = destinatie2;
    }

    public String getDestinatie3() {
        return destinatie3;
    }

    public void setDestinatie3(String destinatie3) {
        this.destinatie3 = destinatie3;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "destinatie1='" + destinatie1 + '\'' +
                ", destinatie2='" + destinatie2 + '\'' +
                ", destinatie3='" + destinatie3 + '\'' +
                ", data=" + data +
                ", dataFinal=" + dataFinal +
                '}';
    }
}
