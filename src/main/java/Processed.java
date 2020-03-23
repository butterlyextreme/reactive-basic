import java.util.Objects;

public class Processed {

    public Processed(String entity) {
        this.entity = entity;
    }

    String entity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Processed processed = (Processed) o;
        return Objects.equals(entity, processed.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }
}

