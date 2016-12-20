package com.wolfie.kidspend2.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IoHelper {

    @Expose
    private List<Spend> spends;

    public IoHelper() {
        // No arg ctor for deserialiser.
    }

    public List<Spend> inport(InputStreamReader isr) throws JsonSyntaxException, JsonIOException {
        Gson gson = new Gson();
        IoHelper ioHelper = gson.fromJson(isr, IoHelper.class);
        // Only use girls spends
        spends = new ArrayList<>();
        for (Spend spend : ioHelper.spends) {
            spend.trim();
            for (Girl girl : Girl.values()) {
                if (girl.name().toLowerCase().equals(spend.getGirl().toLowerCase())) {
                    spend.setGirl(girl.name());     // Use correct casing.
                    spends.add(spend);
                    break;      // Found this girl, on to the next spend.
                }
            }
        }
        return spends;
    }

    public String export(List<Spend> spends) {
        this.spends = spends;
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }

    public List<Spend> getSpends() {
        return spends;
    }

}
