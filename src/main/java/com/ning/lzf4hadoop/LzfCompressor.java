package com.ning.lzf4hadoop;

import java.io.IOException;

import com.ning.compress.lzf.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.Compressor;

public class LzfCompressor implements Compressor
{
    public int compress(byte[] buffer, int offset, int length) throws IOException
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public void end() {
        // No-op
    }

    public void finish() {
        // TODO Auto-generated method stub
        
    }

    public boolean finished() {
        // TODO Auto-generated method stub
        return false;
    }

    public long getBytesRead() {
        // TODO Auto-generated method stub
        return 0;
    }

    public long getBytesWritten() {
        // TODO Auto-generated method stub
        return 0;
    }

    public boolean needsInput() {
        // TODO Auto-generated method stub
        return false;
    }

    /* Added in Hadoop 0.21.0... let's implement, but can't yet
     * mark with @Override
     */
//    @Override
    public synchronized void reinit(Configuration conf) {
        reset();
    }
    
    public void reset() {
        // TODO Auto-generated method stub
    }

    public void setDictionary(byte[] buffer, int offset, int length) {
        // No-op
    }

    public void setInput(byte[] buffer, int offset, int length) {
        // TODO Auto-generated method stub
        
    }

}
