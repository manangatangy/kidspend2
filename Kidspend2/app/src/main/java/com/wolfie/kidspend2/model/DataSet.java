package com.wolfie.kidspend2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This is a collection of Spend's passed between the database and the UI.
 */
public class DataSet {

    private List<Spend> mSpends = new ArrayList<>();
    private List<Spend> mDateSortedSpends = new ArrayList<>();

    public List<Spend> getSpends() {
        return mSpends;
    }

    public List<Spend> getDateSortedSpends() {
        return mDateSortedSpends;
    }

    public DataSet(List<Spend> spends) {
        this.mSpends = spends;
        // Also create a list of date sorted spends.
        for (Spend spend : mSpends) {
            mDateSortedSpends.add(spend);
        }
        DataSet.sortOnDate(mDateSortedSpends);
    }

    /**
     * Sort on SPENDS_TYPE and SPENDS_CREATED
     * @param spends
     */
    public static void sort(List<Spend> spends) {
        Collections.sort(spends, new Comparator<Spend>() {
            /**
             * @return an integer < 0 if {@code lhs} is less than {@code rhs}, 0 if they are
             *         equal, and > 0 if {@code lhs} is greater than {@code rhs}.
             */
            @Override
            public int compare(Spend lhs, Spend rhs) {
                int compare = lhs.getSpendType().compareToIgnoreCase(rhs.getSpendType());
                if (compare == 0) {
                    compare = lhs.getCreatedSortable().compareToIgnoreCase(rhs.getCreatedSortable());
                }
                return compare;
            }
        });
    }

    public static void sortOnDate(List<Spend> spends) {
        Collections.sort(spends, new Comparator<Spend>() {
            @Override
            public int compare(Spend lhs, Spend rhs) {
                return lhs.getCreatedSortable().compareToIgnoreCase(rhs.getCreatedSortable());
            }
        });
    }
}
