package net.praqma.prqa.execute;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class StreamGobbler extends Thread {
    protected static Logger logger = java.util.logging.Logger.getLogger(StreamGobbler.class.getName());
    public static final String linesep = System.getProperty("line.separator");

    InputStream is;
    PrintStream printStream;

    public StringBuffer sres;
    public List<String> lres;

    StreamGobbler(InputStream is) {
        this.is = is;
        lres = new ArrayList<String>();
        sres = new StringBuffer();
    }

    StreamGobbler(InputStream is, PrintStream printStream) {
        this.is = is;
        this.printStream = printStream;
        lres = new ArrayList<String>();
        sres = new StringBuffer();
    }

    public StringBuffer getResultBuffer() {
        return sres;
    }

    public List<String> getResultList() {
        return lres;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is/* , "UTF-8" */);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ((line = br.readLine()) != null) {
                logger.info( line );
                lres.add(line);
                if (printStream != null) {
                    printStream.println(line);
                    printStream.flush();
                }
            }

			/* Building buffer */
            for (int i = 0; i < lres.size() - 1; ++i) {
                sres.append(lres.get(i) + linesep);
            }

            if (lres.size() > 0) {
                sres.append(lres.get(lres.size() - 1));
            }

            synchronized (this) {
                notifyAll();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
