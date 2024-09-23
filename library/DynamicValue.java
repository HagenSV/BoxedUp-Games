package library;

public class DynamicValue {

    int startValue;
    int targetValue;
    long startTime;
    long interTime;

    public DynamicValue(){
        setValue(0);
    }
    
    public DynamicValue(int startValue){
        setValue(startValue);
    }

    /**
     * Immediately sets the value
     * Note: this will cancel any previous interpolation
     * @param value the new value to set to
     */
    public void setValue(int value){
        this.startValue = value;
        this.targetValue = value;
        this.startTime = System.currentTimeMillis();
        this.interTime = 1;
    }

    /**
     * Causes the value to approach a target value over a specified period of time
     * Note: this halts the previous interpolation and sets the starting location of the current interpolation at the current position
     * @param targetValue the value to approach
     * @param ms the time in miliseconds to reach the target value
     */
    public void interpolate(int targetValue, long ms){
        this.startValue = getValue();
        this.targetValue = targetValue;
        this.startTime = System.currentTimeMillis();
        this.interTime = ms;
    }

    /**
     * 
     * @return the current value
     */
    public int getValue(){
        long deltaTime = System.currentTimeMillis()-startTime;

        return (int)((targetValue-startValue)*Math.min((float)deltaTime/interTime,1.0f)+startValue);
    }

    public boolean isInterpolating(){
        long deltaTime = System.currentTimeMillis()-startTime;
        return deltaTime/interTime < 1;
    }
}
