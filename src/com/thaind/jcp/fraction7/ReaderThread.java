package com.thaind.jcp.fraction7;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReaderThread extends Thread {
    private static final int BUFF = 512;
    private final Socket socket;
    private final InputStream inputStream;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
    }

    @Override
    public void interrupt() {
        try {
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buff = new byte[BUFF];
            while (true) {
                int count = inputStream.read(buff);
                if (count < 0) {
                    break;
                } else if (count > 0) {
                    processBuffer(buff, count);
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }

    public void processBuffer(byte[] buffer, int count) {
        // testing only no need process
    }
}
