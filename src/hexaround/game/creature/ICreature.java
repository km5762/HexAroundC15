package hexaround.game.creature;

public interface ICreature {
    CreatureName getName();
    int getMaxDistance();
    boolean hasProperty(CreatureProperty property);

}
