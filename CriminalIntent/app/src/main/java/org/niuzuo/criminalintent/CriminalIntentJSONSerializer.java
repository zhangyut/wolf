package org.niuzuo.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.Writer;

/**
 * Created by zdns on 16/5/23.
 */
public class CriminalIntentJSONSerializer {
    private Context mContext;
    private String mFilename;
    public CriminalIntentJSONSerializer (Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for (Crime c : crimes) {
            try {
                array.put(c.toJSON());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public ArrayList<Crime> loadCrimes() throws IOException,JSONException {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        try {
            InputStream in= mContext.openFileInput(mFilename);
            reader= new BufferedReader(new InputStreamReader(in));
            StringBuffer jsonString= new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null ) {
                jsonString.append(line);
            }
            JSONArray json = (JSONArray)new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < json.length(); i++) {
                crimes.add(new Crime(json.getJSONObject(i)));

            }
        } catch (FileNotFoundException e) {

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return crimes;
    }
}
