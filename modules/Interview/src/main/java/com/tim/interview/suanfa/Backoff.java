package com.tim.interview.suanfa;


import java.util.Random;

/**
 * 指数级退避
 */
public class Backoff {

    /**
     * 最大重试次数
     */
    private static final int MAX_RETRIES = 10;

    /**
     * 最大等待间隔时间
     */
    private static final long MAX_WAIT_INTERVAL = 10 * 1000;

    /**
     * 返回值，指定哪些需要重试，哪些不需要重试
     */
    public enum Results {
        SUCCESS,
        NOT_READY,
        THROTTLED,
        SERVER_ERROR
    }

    /**
     * Performs an asynchronous operation, then polls for the result of the
     * operation using an incremental delay.
     */
    public static void doOperationAndWaitForResult() {

        Random rand = new Random();
        try {
            // Do some asynchronous operation.
            long token = asyncOperation();

            int retries = 0;
            boolean retry;

            do {
                long waitInterval = getWaitTimeExp(retries);
                // 加入抖动（随机延迟）防止连续冲突
                long waitTime = Math.min(waitInterval, MAX_WAIT_INTERVAL) + rand.nextInt((int) waitInterval / 2);

                System.out.print(waitTime + "\n");

                // Wait for the result.
                Thread.sleep(waitTime);

                // Get the result of the asynchronous operation.
                Results result = getAsyncOperationResult(token);

                if (Results.SUCCESS == result) {
                    retry = false;
                } else if (Results.NOT_READY == result) {
                    retry = true;
                } else if (Results.THROTTLED == result) {
                    retry = true;
                } else if (Results.SERVER_ERROR == result) {
                    retry = true;
                }
                else {
                    // Some other error occurred, so stop calling the API.
                    retry = false;
                }

            } while (retry && (retries++ < MAX_RETRIES));
        }

        catch (Exception ex) {
        }
    }

    /**
     * Returns the next wait interval, in milliseconds, using an exponential
     * backoff algorithm.
     */
    public static long getWaitTimeExp(int retryCount) {

        long waitTime = ((long) Math.pow(2, retryCount) * 100L);

        return waitTime;
    }

    public static long asyncOperation() {
        return System.currentTimeMillis();
    }

    public static Results getAsyncOperationResult(long token) {
        return Results.NOT_READY;
    }

    public static void main(String[] args) {
        doOperationAndWaitForResult();
    }
}
