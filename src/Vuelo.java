public class Vuelo {
    private int idVuelo;
    private int idAvion;
    private String origen;
    private String destino;
    private String horaSalida;
    private String horaLlegada;
    private String fecha;
    private String idTrayecto;

    public Vuelo(int idVuelo, String origen, String destino, String horaSalida, String horaLlegada, String fecha) {
        this.idVuelo = idVuelo;
        this.idAvion = idAvion;
        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.fecha = fecha;
        this.idTrayecto = idTrayecto;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public int getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdTrayecto() {
        return idTrayecto;
    }

    public void setIdTrayecto(String idTrayecto) {
        this.idTrayecto = idTrayecto;
    }
}
