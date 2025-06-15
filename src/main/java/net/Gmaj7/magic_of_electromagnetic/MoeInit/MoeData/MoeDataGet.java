package net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeData;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MirageEntity;

public interface MoeDataGet {
    MoeProtective getProtective();

    boolean hasMirageEntity();

    MirageEntity getMirageEntity();

    void setMirageEntity(MirageEntity mirageEntity);
}
