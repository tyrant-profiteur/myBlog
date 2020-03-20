package com.ywknight.blog.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    /**
     * 分割 1，2，3 -》 1 2 3
     * @param ids
     * @return
     */
    public static List<Long> converToList(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null){
            String[] idsArrays = ids.split(",");
            for (int i = 0; i < idsArrays.length; i++) {
                    list.add(new Long(idsArrays[i]));
            }
        }
        return list;
    }
}
