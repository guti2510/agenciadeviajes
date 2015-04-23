package com.luispc.travelangency;

/**
 * Created by LuisPC on 4/20/15.
 */
public class InfoReservacion {

    private String _Nombre;
    private String _CantidadPersonas;
    private String _DiaReservado;
    private String _FechaFinal;
    private String _PrecioFinal;
    private String _TipoReserva;
    private String _CorreoUsuario;

    public InfoReservacion(){}

    public InfoReservacion(String _Nombre, String _CantidadPersonas, String _DiaReservado,
                           String _FechaFinal, String _PrecioFinal, String _TipoReserva, String _CorreoUsuario) {
        this._Nombre = _Nombre;
        this._CantidadPersonas = _CantidadPersonas;
        this._DiaReservado = _DiaReservado;
        this._FechaFinal = _FechaFinal;
        this._PrecioFinal = _PrecioFinal;
        this._TipoReserva = _TipoReserva;
        this._CorreoUsuario = _CorreoUsuario;
    }

    public String getNombre() {
        return _Nombre;
    }

    public String getCantidadPersonas() {
        return _CantidadPersonas;
    }

    public String getDiaReservado() {
        return _DiaReservado;
    }

    public String getFechaFinal() {
        return _FechaFinal;
    }

    public String getPrecioFinal() {
        return _PrecioFinal;
    }

    public String getTipoReserva() {
        return _TipoReserva;
    }

    public String getCorreoUsuario() {
        return _CorreoUsuario;
    }
}