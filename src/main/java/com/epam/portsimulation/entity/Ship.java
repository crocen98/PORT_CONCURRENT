package com.epam.portsimulation.entity;

import com.epam.portsimulation.service.port.Port;
import com.epam.portsimulation.service.state.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ship implements Runnable, Serializable {
    private static final Logger LOGGER = LogManager.getLogger(Ship.class);
    private String name;
    private ShipState state;
    private Purpose purpose;
    private int containersCount;
    private final int maxCapacity;

    private ReentrantLock lock = new ReentrantLock();
    private Condition conditionLoading = lock.newCondition();
    private Condition conditionUploading = lock.newCondition();


    public Ship(int containersCount, int maxCapacity, String name, Purpose purpose) {
        this.containersCount = containersCount;
        this.maxCapacity = maxCapacity;
        this.name = name;
        this.purpose = purpose;
    }

    private Port port = Port.getInstance();



    @Override
    public void run() {
        try {
            lock.lock();
            while (port.getContainersCount().intValue() < this.maxCapacity && purpose == Purpose.LOADING){
                LOGGER.info("await Loading " + name);
                conditionLoading.await();
                LOGGER.info("Ship: " + name + " await + 111111111111111111111111111111111111111111111111111111111111111111111111111");
            }

            while (((port.getMaxCapacity() - port.getContainersCount().intValue()) <= this.getContainersCount()) && (purpose == Purpose.UNLOADING)){
                LOGGER.info("await UNLoading " + name);

                conditionUploading.await();
                LOGGER.info("Ship: " + name + " await + 111111111111111111111111111111111111111111111111111111111111111111111111111");

            }
            LOGGER.info(port.getContainersCount());

            //Semaphore semaphore = port.getSemaphore();
//            try {
//                semaphore.acquire();
              //  LOGGER.info("Ship: " + name + " in dock + " + semaphore.availablePermits());
                if (this.purpose == Purpose.LOADING) {
                    LOGGER.info(port.getContainersCount() + " Containers count!!!! LOADING");
                    port.getContainersCount().set(port.getContainersCount().intValue() - this.maxCapacity);
                    conditionUploading.signalAll();
                } else if(this.purpose == Purpose.UNLOADING) {
                    LOGGER.info(port.getContainersCount() + " Containers count!!!! UNLOADING");
                    port.getContainersCount().addAndGet(this.containersCount);
                    conditionLoading.signalAll();
                }

            LOGGER.info("END " + name);
              //  Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                semaphore.release();
//            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

//        Semaphore semaphore = port.getSemaphore();
//        try {
//            semaphore.acquire();
//            LOGGER.info("Ship: " + name + " in dock + " + semaphore.availablePermits() );
//            if(this.purpose == Purpose.LOADING){
//                port.getContainersCount().set(port.getContainersCount().intValue() - this.maxCapacity);
//            } else {
//                port.getContainersCount().addAndGet(this.containersCount);
//            }
//            lockCondition.signalAll();
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            semaphore.release();
//        }
    }


    public ShipState getState() {
        return state;
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public int getContainersCount() {
        return containersCount;
    }

    public void setContainersCount(int containersCount) {
        this.containersCount = containersCount;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public void setLock(ReentrantLock lock) {
        this.lock = lock;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", purpose=" + purpose +
                ", containersCount=" + containersCount +
                ", maxCapacity=" + maxCapacity +
                ", port=" + port +
                '}';
    }
}
