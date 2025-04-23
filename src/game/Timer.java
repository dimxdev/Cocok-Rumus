package game;

import javax.swing.*;

public class Timer extends Thread {

    private volatile boolean isRunning;
    private volatile boolean isStopped;
    private int time;

    public Timer(PapanPermainan papanPermainan) {
        this.isRunning = true;
        this.isStopped = false;
        this.time = 0;
    }

    @Override
    public void run() {
        while (!isStopped) {
            synchronized (this) {
                while (!isRunning && !isStopped) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        if (isStopped) {
                            break;
                        }
                    }
                }
            }

            if (isStopped) {
                break;
            }

            try {
                Thread.sleep(1000);
                synchronized (this) {
                    if (isRunning) {
                        time++;
                        SwingUtilities.invokeLater(() -> {
                            PapanPermainan.getInstance().setTimeLabelText(formatTime(time));
                        });
                    }
                }
            } catch (InterruptedException e) {
                if (isStopped) {
                    break;
                }
            }
        }
    }

    public synchronized void setTimeRunning(boolean running) {
        this.isRunning = running;
        if (running) {
            notify();
        }
    }

    public synchronized void stopTimer() {
        this.isStopped = true;
        this.isRunning = false;
        interrupt();
    }

    public synchronized int getTime() {
        return time;
    }

    public String formatTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public synchronized void resetTimer() {
        this.time = 0;
        SwingUtilities.invokeLater(() -> {
            PapanPermainan.getInstance().setTimeLabelText(formatTime(time));
        });
    }
}
