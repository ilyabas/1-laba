/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Harry
 */
public class Queue {
    
    private int id;
    private int maxCount;
    private int curCount;

    public Queue(int id, int maxCount, int curCount) {
        this.id = id;
        this.maxCount = maxCount;
        this.curCount = curCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getCurCount() {
        return curCount;
    }

    public void setCurCount(int curCount) {
        this.curCount = curCount;
    }

    @Override
    public String toString() {
        return "Queue " + id + " {" + "maxCount=" + maxCount + ", curCount=" + curCount + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.maxCount;
        hash = 73 * hash + this.curCount;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Queue other = (Queue) obj;
        if (this.maxCount != other.maxCount) {
            return false;
        }
        if (this.curCount != other.curCount) {
            return false;
        }
        return true;
    }
    
    
}
