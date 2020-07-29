package org.tools.fx.library.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileSearchRecord {
//    private final LongProperty crewId = new SimpleLongProperty();
//    private final StringProperty crewName = new SimpleStringProperty();
//
//    private final LongProperty fileId = new SimpleLongProperty();
//    private final LongProperty hdID = new SimpleLongProperty();
//    private final LongProperty ptID = new SimpleLongProperty();
//
//    private final StringProperty hdSN = new SimpleStringProperty();
//    private final StringProperty ptSN = new SimpleStringProperty();
//    private final StringProperty fileName = new SimpleStringProperty();
//    private final StringProperty filePath = new SimpleStringProperty();
//
//    FileSearchRecord(long id, String name, long fId, long hId, long pId, String hSN, String pSN,
//            String fName, String fPath) {
//        crewId.set(id);
//        crewName.set(name);
//        fileId.set(fId);
//        hdID.set(hId);
//        ptID.set(pId);
//
//        hdSN.set(hSN);
//        ptSN.set(pSN);
//        fileName.set(fName);
//        filePath.set(fPath);
//    }
//
//    public LongProperty crewId() {
//        return crewId;
//    }
//
//    public final long getCrewId() {
//        return crewId.get();
//    }
//
//    public final void setCrewId(long id) {
//        crewId.set(id);
//    }
//
//    public StringProperty crewName() {
//        return crewName;
//    }
//
//    public final String getCrewName() {
//        return crewName.get();
//    }
//
//    public final void setCrewName(String name) {
//        crewName.set(name);
//    }
//
//    public LongProperty fileId() {
//        return fileId;
//    }
//
//    public Long getFileId() {
//        return fileId.get();
//    }
//
//    public LongProperty hdID() {
//        return hdID;
//    }
//
//    public Long getHdID() {
//        return hdID.get();
//    }
//
//    public LongProperty ptID() {
//        return ptID;
//    }
//
//    public Long getPtID() {
//        return ptID.get();
//    }
//
//    public StringProperty hdSN() {
//        return hdSN;
//    }
//
//    public String getHdSN() {
//        return hdSN.get();
//    }
//
//    public StringProperty ptSN() {
//        return ptSN;
//    }
//
//    public String getPtSN() {
//        return ptSN.get();
//    }
//
//    public StringProperty fileName() {
//        return fileName;
//    }
//
//    public String getFileName() {
//        return fileName.get();
//    }
//
//    public StringProperty filePath() {
//        return filePath;
//    }
//
//    public String getFilePath() {
//        return filePath.get();
//    }

    
    private final LongProperty fileId = new SimpleLongProperty();
    private final LongProperty hdID = new SimpleLongProperty();
    private final LongProperty ptID = new SimpleLongProperty();

    private final StringProperty hdSN = new SimpleStringProperty();
    private final StringProperty ptSN = new SimpleStringProperty();
    private final StringProperty fileName = new SimpleStringProperty();
    private final StringProperty filePath = new SimpleStringProperty();


}
