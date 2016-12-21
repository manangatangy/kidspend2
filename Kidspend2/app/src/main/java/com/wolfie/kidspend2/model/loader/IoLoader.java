/**
 * (C) 2016. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 * copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 * documentation may be reproduced in any form by any means without prior written authorization of National Australia
 * Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 */

package com.wolfie.kidspend2.model.loader;

import android.support.annotation.Nullable;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.wolfie.kidspend2.model.IoHelper;
import com.wolfie.kidspend2.model.Spend;
import com.wolfie.kidspend2.model.database.Source;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Performs export and import tasks in a background thread.
 */
public class IoLoader {

    private Source mDataSource;

    public IoLoader(Source dataSource) {
        mDataSource = dataSource;
    }

    public class IoResult {
        public String mSuccessMessage;
        public String mFailureMessage;
    }

    public class SuccessResult extends IoResult {
        public SuccessResult(String successMessage) {
            mSuccessMessage = successMessage;
        }
    }

    public class FailureResult extends IoResult {
        public FailureResult(String failureMessage) {
            mFailureMessage = failureMessage;
        }
    }

    public void export(File file, AsyncListeningTask.Listener<IoResult> listener) {
        new ExportTask(listener).execute(file);
    }

    public void inport(File file, AsyncListeningTask.Listener<IoResult> listener) {
        new ImportTask(listener).execute(file);
    }

    private class ExportTask extends AsyncListeningTask<File, IoResult> {
        public ExportTask(@Nullable Listener<IoResult> listener) {
            super(listener);
        }
        @Override
        public IoResult runInBackground(File file) {
            List<Spend> spends = mDataSource.read(null);
            String json = new IoHelper().export(spends);
            IoResult ioResult = null;
            FileOutputStream fos = null;
            BufferedWriter bw = null;
            try {
                fos = new FileOutputStream(file);
                bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
                bw.write(json);
                ioResult = new SuccessResult("Exported " + spends.size() + " spends");
            } catch (FileNotFoundException fnfe) {
                return new FailureResult("FileNotFound opening\n" + file.getName());
            } catch (UnsupportedEncodingException usce) {
                return new FailureResult("UnsupportedEncodingException\n" + file.getPath());
            } catch (IOException ioe) {
                return new FailureResult("IOException writing\n" + file.getPath());
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException ioe) {
                    ioResult = new FailureResult("IOException closing\n" + file.getPath());
                    // Won't be returned if exception was thrown prior to the finally clause executing.
                }
            }
            return ioResult;
        }
    }

    private class ImportTask extends AsyncListeningTask<File, IoResult> {
        public ImportTask(@Nullable Listener<IoResult> listener) {
            super(listener);
        }
        @Override
        public IoResult runInBackground(File file) {
            IoResult ioResult = null;
            InputStreamReader isr = null;
            try {
                FileInputStream fis = new FileInputStream(file);
                isr = new InputStreamReader(fis);
                List<Spend> spends = new IoHelper().inport(isr);

                mDataSource.deleteAll();
                for (int i = 0; i < spends.size(); i++) {
                    mDataSource.insert(spends.get(i));
                }
                ioResult = new SuccessResult("Imported " + spends.size() + " spends");
            } catch (FileNotFoundException fnfe) {
                return new FailureResult("FileNotFound opening\n" + file.getPath());
            } catch (JsonIOException jioe) {
                return new FailureResult("JsonIOException reading\n" + file.getPath());
            } catch (JsonSyntaxException jse) {
                return new FailureResult("JsonSyntaxException parsing\n" + file.getPath());
            } finally {
                try {
                    if (isr != null) {
                        isr.close();
                    }
                } catch (IOException ioe) {
                    ioResult = new FailureResult("IOException closing\n" + file.getPath());
                    // Won't be returned if exception was thrown prior to the finally clause executing.
                }
            }
            return ioResult;
        }
    }

}
