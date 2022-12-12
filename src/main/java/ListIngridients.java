import java.util.List;

public class ListIngridients {
    private Boolean success;
    private List<Ingridient> data;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Ingridient> getData() {
        return data;
    }

    public void setData(List<Ingridient> data) {
        this.data = data;
    }
}
