package com.fsd.file.utils.excel2007;

import java.util.List;

/**
 * @Author HongYu Zhang
 * @description
 * @version 1.0
 */
public interface IRowReader {

    Object getRowData(int sheetNo, int curRow, List<String> rowlist);

    String getErrorMessage();

    void checkCellInfo(int sheetNo, int curRow, List<String> rowData);

    void setDataTime(String dataTime);

    String getDataTime();
}
