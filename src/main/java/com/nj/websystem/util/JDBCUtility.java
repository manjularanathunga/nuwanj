package com.nj.websystem.util;

import com.nj.websystem.rest.QueryResponseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JDBCUtility {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void runQuary(String sqlStr) {
        List<Map<String, Object>> resultLst = null;
        resultLst = jdbcTemplate.queryForList(sqlStr);
        if (!commonUtility.isObjEmpty(resultLst)) {
            if (!resultLst.isEmpty()) {
                List<String> headreNameList = new ArrayList<>();
                for (Map.Entry e : resultLst.get(0).entrySet()) {
                    headreNameList.add(e.getKey().toString());
                }

                List<QueryResponseData> dataList = new ArrayList<>();
                resultLst.stream().forEach(s -> {
                    QueryResponseData resultNode = new QueryResponseData();
                    for (Map.Entry entry : s.entrySet()) {
                        resultNode.getData().add(entry.getValue() == null ? StringUtils.EMPTY : entry.getValue().toString());
                    }
                    dataList.add(resultNode);
                });

            } else {
                //results not found
            }
        }

    }
}
