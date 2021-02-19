package es.udc.ws.runfic.model.carrera;

import java.time.LocalDateTime;

public class Carrera {
    private Long carreraId;
    private String ciudad;
    private String descripcion;
    private LocalDateTime fechaCarrera;
    private float precioCarrera;
    private int maxParticipantes;
    private LocalDateTime altaCarrera;
    private int numeroInscritos;

    public Carrera(String ciudad, String descripcion,LocalDateTime fechaCarrera, float precioCarrera, int maxParticipantes) {
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.fechaCarrera = fechaCarrera;
        this.precioCarrera = precioCarrera;
        this.maxParticipantes = maxParticipantes;
    }

    public Carrera(Long carreraId,String ciudad, String descripcion,LocalDateTime fechaCarrera, float precioCarrera, int maxParticipantes) {
        this(ciudad, descripcion, fechaCarrera, precioCarrera,maxParticipantes);
        this.carreraId = carreraId;
    }

    public Carrera(Long carreraId,String ciudad, String descripcion,LocalDateTime fechaCarrera, float precioCarrera, int maxParticipantes, LocalDateTime altaCarrera) {
        this(carreraId,ciudad, descripcion, fechaCarrera, precioCarrera,maxParticipantes);
        this.altaCarrera = (altaCarrera != null) ? altaCarrera.withNano(0) : null;
    }

    public Carrera(Long carreraId,String ciudad, String descripcion,LocalDateTime fechaCarrera, float precioCarrera, int maxParticipantes, LocalDateTime altaCarrera, int numeroInscritos) {
        this(carreraId,ciudad, descripcion, fechaCarrera, precioCarrera,maxParticipantes, altaCarrera);
        this.numeroInscritos=numeroInscritos;
    }

    public Long getCarreraId() {return carreraId;}

    public void setCarreraId(Long carreraId) {this.carreraId = carreraId;}

    public String getCiudad() {return ciudad;}

    public void setCiudad(String ciudad) {this.ciudad = ciudad;}

    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public LocalDateTime getFechaCarrera() {return fechaCarrera;}

    public void setFechaCarrera(LocalDateTime fechaCarrera) {this.fechaCarrera = fechaCarrera;}

    public float getPrecioCarrera() {return precioCarrera;}

    public void setPrecioCarrera(float precioCarrera) {this.precioCarrera = precioCarrera;}

    public int getMaxParticipantes() {return maxParticipantes;}

    public void setMaxParticipantes(int maxParticipantes) {this.maxParticipantes = maxParticipantes;}

    public LocalDateTime getAltaCarrera() {return altaCarrera;}

    public void setAltaCarrera(LocalDateTime altaCarrera) {this.altaCarrera = (altaCarrera != null) ? altaCarrera.withNano(0) : null;}

    public int getNumeroInscritos() { return numeroInscritos; }

    public void setNumeroInscritos(int numeroInscritos) { this.numeroInscritos = numeroInscritos; }

    public void incrementarInscritos(){this.numeroInscritos++;}

    public void decrementarInscritos(){this.numeroInscritos--;}

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if(getClass() != object.getClass())
            return false;
        Carrera tmp = (Carrera) object;
        if(altaCarrera==null){
            if(tmp.altaCarrera!=null)
                return false;
        } else if (!altaCarrera.equals(tmp.altaCarrera))
            return false;
        if(fechaCarrera==null){
            if(tmp.fechaCarrera!=null)
                return false;
        } else if (!altaCarrera.equals(tmp.altaCarrera))
            return false;
        if (descripcion == null) {
            if (tmp.descripcion != null)
                return false;
        } else if (!descripcion.equals(tmp.descripcion))
            return false;
        if (carreraId == null) {
            if (tmp.carreraId != null)
                return false;
        } else if (!carreraId.equals(tmp.carreraId))
            return false;
        if (Float.floatToIntBits(precioCarrera) != Float.floatToIntBits(tmp.precioCarrera))
            return false;
        if (maxParticipantes != tmp.maxParticipantes)
            return false;
        if (ciudad == null) {
            if (tmp.ciudad != null)
                return false;
        }else if (!ciudad.equals(tmp.ciudad))
            return false;
        return  true;
    }

    @Override
    public int hashCode() {
        final int prime = 43;
        int result = 1;
        result = prime * result + ((altaCarrera == null) ? 0 : altaCarrera.hashCode());
        result = prime * result + ((fechaCarrera == null) ? 0 : fechaCarrera.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((carreraId == null) ? 0 : carreraId.hashCode());
        result = prime * result + Float.floatToIntBits(precioCarrera);
        result = prime * result + maxParticipantes;
        result = prime * result + ((ciudad == null) ? 0 : ciudad.hashCode());
        return result;
    }
}
