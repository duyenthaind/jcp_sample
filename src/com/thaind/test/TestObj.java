package com.thaind.test;

public class TestObj {
    private String test;
    private String tes3;

    public TestObj() {
    }

    public TestObj(String test, String tes3) {
        this.test = test;
        this.tes3 = tes3;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTes3() {
        return tes3;
    }

    public void setTes3(String tes3) {
        this.tes3 = tes3;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((test == null) ? 0 : test.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestObj other = (TestObj) obj;
        if (test == null) {
            if (other.test != null)
                return false;
        } else if (!test.equals(other.test))
            return false;
        return true;
    }

    

    

}
