package com.example.calculadora;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Resultado implements Parcelable {

    int index,aciertos,fallos;
    double porcentaje;

    public Resultado(int index, int aciertos, int fallos, double porcentaje) {
        this.index = index;
        this.aciertos = aciertos;
        this.fallos = fallos;
        this.porcentaje = porcentaje;
    }

    protected Resultado(Parcel in) {
        index = in.readInt();
        aciertos = in.readInt();
        fallos = in.readInt();
        porcentaje = in.readDouble();
    }

    public static final Creator<Resultado> CREATOR = new Creator<Resultado>() {
        @Override
        public Resultado createFromParcel(Parcel in) {
            return new Resultado(in);
        }

        @Override
        public Resultado[] newArray(int size) {
            return new Resultado[size];
        }
    };

    public int getIndex() {
        return index;
    }

    public int getAciertos() {
        return aciertos;
    }

    public int getFallos() {
        return fallos;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

    public void setFallos(int fallos) {
        this.fallos = fallos;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }


    @Override
    public String toString() {
        return "Resultado{" +
                "index=" + index +
                ", aciertos=" + aciertos +
                ", fallos=" + fallos +
                ", porcentaje=" + porcentaje +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(index);
        parcel.writeInt(aciertos);
        parcel.writeInt(fallos);
        parcel.writeDouble(porcentaje);
    }
}
