package com.ning.lzf4hadoop;

import java.io.IOException;

import com.ning.compress.lzf.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.Decompressor;

public class LzfDecompressor implements Decompressor
{
    protected byte[] _inputBuffer;
    protected int _inputOffset;
    protected int _inputLength;

    // Not optimal (ideally would use segments), but has to do
    
    protected byte[] _encodedBuffer;
    protected int _encodedPtr;
    protected int _encodedEnd;

    protected boolean _finished = false;
    
    protected long _bytesRead;
    protected long _bytesWritten;
    
    public int decompress(byte[] buffer, int offset, int length)
    {
        if (buffer == null || offset < 0 || ((offset + length) > buffer.length)) {
            throw new IllegalArgumentException("Bad argument(s) to decompress; buffer "
                    +buffer+", offset "+offset+", length "+length);
        }
        if (_inputBuffer != null) {
            _encodedBuffer = LZFDecoder.decode(_inputBuffer, _inputOffset, _inputLength);
            _encodedPtr = 0;
            _encodedEnd = _encodedBuffer.length;
            _inputBuffer = null;
            _inputOffset = _inputLength = 0;
        }
        if (_encodedBuffer == null || _encodedPtr >= _encodedEnd) {
            return 0;
        }
        int actualLen = Math.min(length, (_encodedEnd - _encodedPtr));
        System.arraycopy(_encodedBuffer, _encodedPtr, buffer, offset, actualLen);
        _bytesWritten += actualLen;
        if ((_encodedPtr += actualLen) >= _encodedEnd) {
            _encodedBuffer = null;
            _encodedPtr = _encodedEnd = 0;
        }
        return actualLen;
    }

    public void end() {
        reset();
    }

    public boolean finished() {
        return _finished;
    }

    public boolean needsDictionary() {
        return false;
    }

    public boolean needsInput() {
        return (_inputBuffer == null) && (_encodedBuffer == null);
    }
    
    public void reset() {
        _finished = false;
        _bytesRead = _bytesWritten = 0L;
        _inputBuffer = null;
        _inputOffset = _inputLength = 0;
        _encodedBuffer = null;
    }

    public void setDictionary(byte[] buffer, int offset, int length) {
        // No-op
    }

    public void setInput(byte[] buffer, int offset, int length)
    {
        // sanity check first:
        if (_inputBuffer != null || _encodedBuffer != null) {
            throw new IllegalStateException("Should not call setInput() before previous input has been fully consumed");
        }
        /* Assumption here is that we need not make a copy; seems sensible as copying
         * adds overhead which is unlikely to be needed with hadoop tasks...
         */
        _bytesRead += length;
        _inputBuffer = buffer;
        _inputOffset = offset;
        _inputLength = length;
    }
}
