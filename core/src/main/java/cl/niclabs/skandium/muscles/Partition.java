package cl.niclabs.skandium.muscles;

public interface Partition<P, R> extends Muscle<P, R> {

    R[] partition(P[] param) throws Exception;
}
