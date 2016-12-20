package com.wolfie.kidspend2.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to organise the spends, prior to displaying them.  Typically all
 * the records in mSpends have the same spendType (which is held in mHeading).
 * All the spends in mSpends belong to the same spendType (named as the heading).
 */
public class SpendGroup {
    private String mHeading;
    private List<Spend> mSpends;
    private int mTotal;

    public SpendGroup(String heading, List<Spend> spends) {
        mHeading = heading;
        mSpends = spends;
    }

    public String getHeading() {
        return mHeading;
    }
    public List<Spend> getSpends() {
        return mSpends;
    }
    public int getTotal() {
        return mTotal;
    }

    /**
     * Build a single SpendGroup from the DataSet.  If the spendType is non-null, then return
     * a the one SpendGroup, whose heading matches the specified heading.  If spendType is null
     * then make an aggregate list of Spends (with id=-1 so that indicates this Spend cannot be
     * edited).
     * Assumes the Spends in the DataSet are ordered by spendType name.
     */
    public static SpendGroup buildGroup(String heading, DataSet dataSet) {
        List<SpendGroup> groups = new ArrayList<>();
        List<Spend> aggregate = new ArrayList<>();
        String currentSpendType = null;
        int aggregateSpendTotal = 0;
//        int groupTotal = 0;
        List<Spend> currentSpends = null;
        for (Spend spend : dataSet.getSpends()) {
            Log.d("kidspend2", "SpendGroup.buildGroups(): spendType:" + spend.getSpendType() + ", created=" + spend.getCreated());
            if (!spend.getSpendType().equals(currentSpendType)) {
                // This spend is in a different group to the previous one, close
                // off the current list (if one has been started).
                if (currentSpends != null && currentSpendType != null) {
                    SpendGroup group = new SpendGroup(currentSpendType, currentSpends);
                    groups.add(group);
                    aggregate.add(Spend.create(null, currentSpendType, aggregateSpendTotal, null));
                    currentSpends = null;
                    currentSpendType = null;
                    aggregateSpendTotal = 0;
                }
            }
            boolean mustCollect = (heading == null || spend.getSpendType().equals(heading));
            if  (mustCollect) {
                if  (currentSpends == null) {
                    // This group name must be collected and we have not yet started a
                    // group for, so start a new current group for it.
                    currentSpends = new ArrayList<>();
                    currentSpendType = spend.getSpendType();
                }
                currentSpends.add(spend);
                aggregateSpendTotal += spend.getAmount();
            }
        }
        // If there is a current group being collected, close it off and add it in.
        if (currentSpends != null && currentSpendType != null) {
            SpendGroup group = new SpendGroup(currentSpendType, currentSpends);
            groups.add(group);
            aggregate.add(Spend.create(null, currentSpendType, aggregateSpendTotal, null));
        }
        return (heading != null) ? groups.get(0) : new SpendGroup("Summary", aggregate);
    }

    /**
     * Build a list of one SpendGroup (with the specified heading) from the DataSet.
     * The single SpendGroup contains all the Spends from the DataSet, ordered.
     * As a convenience, a null DataSet parameter means create an empty dataSet in the target.
     */
    public static List<SpendGroup> buildSingleGroup(String heading, DataSet dataSet) {
        List<Spend> spends = new ArrayList<>();
        if (dataSet != null) {
            for (Spend spend : dataSet.getSpends()) {
                spends.add(spend);
            }
            DataSet.sortOnDate(spends);          // Do the sort, based only on the entry.name
        }
        SpendGroup group = new SpendGroup(heading, spends);
        List<SpendGroup> groups = new ArrayList<>();
        groups.add(group);
        return groups;
    }

    public static List<String> buildHeadingsList(DataSet dataSet) {
        List<String> headings = new ArrayList<>();
        String currentSpendType = null;
        for (Spend spend : dataSet.getSpends()) {
            if (!spend.getSpendType().equals(currentSpendType)) {
                // This entry has a different group to the previous one, add to the list
                currentSpendType = spend.getSpendType();
                headings.add(currentSpendType);
            }
        }
        return headings;
    }

}
