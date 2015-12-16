package com.example.alejofila.nationaldex.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class PokemonDetails {

    private int national_id;
    private Ability[] abilities;
    @SerializedName(value = "catch_rate")
    private int catchRate;
    private Evolution[] evolutions;
    private Description[] descriptions;
    private Type[] types;
    private Move[] moves;
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private String height;
    private String weight;
    private Sprite[] sprites;

    public Ability[] getAbilities() {
        return abilities;
    }

    public void setAbilities(Ability[] abilities) {
        this.abilities = abilities;
    }

    public int getCatchRate() {
        return catchRate;
    }

    public void setCatchRate(int catchRate) {
        this.catchRate = catchRate;
    }

    public Evolution[] getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(Evolution[] evolutions) {
        this.evolutions = evolutions;
    }

    public Description[] getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Description[] descriptions) {
        this.descriptions = descriptions;
    }

    public Type[] getTypes() {
        return types;
    }

    public void setTypes(Type[] types) {
        this.types = types;
    }

    public Move[] getMoves() {
        return moves;
    }

    public void setMoves(Move[] moves) {
        this.moves = moves;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    public void setSprites(Sprite[] sprites) {
        this.sprites = sprites;
    }

    public int getNational_id() {
        return national_id;
    }

    public void setNational_id(int national_id) {
        this.national_id = national_id;
    }
}
