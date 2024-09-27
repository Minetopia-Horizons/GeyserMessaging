package nl.mthorizons.messaging.bedrock.translators;

/*
 * Copyright (c) 2019-2022 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Geyser
 */

import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.geysermc.cumulus.component.Component;
import org.geysermc.geyser.translator.entity.EntityMetadataTranslator;
import org.geysermc.mcprotocollib.protocol.data.game.entity.metadata.Pose;
import org.geysermc.mcprotocollib.protocol.data.game.entity.metadata.type.ByteEntityMetadata;
import org.geysermc.mcprotocollib.protocol.data.game.entity.object.Direction;
import org.geysermc.mcprotocollib.protocol.data.game.entity.object.FallingBlockData;
import org.geysermc.mcprotocollib.protocol.data.game.entity.object.ProjectileData;
import org.geysermc.mcprotocollib.protocol.data.game.entity.object.WardenData;
import org.geysermc.mcprotocollib.protocol.data.game.entity.type.EntityType;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.spawn.ClientboundAddEntityPacket;
import org.cloudburstmc.math.vector.Vector3f;
import org.geysermc.geyser.GeyserImpl;
import org.geysermc.geyser.entity.EntityDefinition;
import org.geysermc.geyser.entity.type.*;
import org.geysermc.geyser.entity.type.player.PlayerEntity;
import org.geysermc.geyser.registry.Registries;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.skin.SkinManager;
import org.geysermc.geyser.text.GeyserLocale;
import org.geysermc.geyser.translator.protocol.PacketTranslator;

import static nl.mthorizons.messaging.utils.registry.CustomRegistries.CUSTOM_ENTITIES;
import static nl.mthorizons.messaging.utils.registry.CustomRegistries.CUSTOM_ENTITY_DEFINITIONS;

public class JavaAddEntityTranslator extends PacketTranslator<ClientboundAddEntityPacket> {
    @Override
    public void translate(GeyserSession session, ClientboundAddEntityPacket packet) {
        Entity entity = null;

        //String def = CUSTOM_ENTITIES.get(packet.getEntityId());
        if (packet.getType().equals(EntityType.ITEM_DISPLAY)) {
            EntityDefinition newDef = CUSTOM_ENTITY_DEFINITIONS.get("modelengine:ambulance_van");

            Vector3f position = Vector3f.from(packet.getX(), packet.getY(), packet.getZ());
            entity = new Entity(session, packet.getEntityId(), session.getEntityCache().getNextEntityId().incrementAndGet(),
                    packet.getUuid(), newDef, position, Vector3f.ZERO, 0,0,0);
            System.out.println(entity.getPropertyManager() == null);

            entity.setFlag(EntityFlag.RIDING, true);
            entity.updateBedrockMetadata();

            System.out.println("i worky worky");
        } else {
            EntityDefinition<?> definition = Registries.ENTITY_DEFINITIONS.get(packet.getType());
            if (definition == null) {
                session.getGeyser().getLogger().debug("Could not find an entity definition with type " + packet.getType());
                return;
            }

            entity = defaultBehaviour(session, definition, packet);
        }

        if (entity == null) return;
        session.getEntityCache().spawnEntity(entity);
    }

    public Entity defaultBehaviour(GeyserSession session, EntityDefinition definition, ClientboundAddEntityPacket packet) {
        Vector3f position = Vector3f.from(packet.getX(), packet.getY(), packet.getZ());
        Vector3f motion = Vector3f.from(packet.getMotionX(), packet.getMotionY(), packet.getMotionZ());
        float yaw = packet.getYaw();
        float pitch = packet.getPitch();
        float headYaw = packet.getHeadYaw();

        if (packet.getType() == EntityType.PLAYER) {

            PlayerEntity entity;
            if (packet.getUuid().equals(session.getPlayerEntity().getUuid())) {
                // Server is sending a fake version of the current player
                entity = new PlayerEntity(session, packet.getEntityId(), session.getEntityCache().getNextEntityId().incrementAndGet(),
                        session.getPlayerEntity().getUuid(), position, motion, yaw, pitch, headYaw, session.getPlayerEntity().getUsername(),
                        session.getPlayerEntity().getTexturesProperty());
            } else {
                entity = session.getEntityCache().getPlayerEntity(packet.getUuid());
                if (entity == null) {
                    GeyserImpl.getInstance().getLogger().error(GeyserLocale.getLocaleStringLog("geyser.entity.player.failed_list", packet.getUuid()));
                    return null;
                }

                entity.setEntityId(packet.getEntityId());
                entity.setPosition(position);
                entity.setYaw(yaw);
                entity.setPitch(pitch);
                entity.setHeadYaw(headYaw);
                entity.setMotion(motion);
            }
            session.getEntityCache().cacheEntity(entity);

            entity.sendPlayer();
            SkinManager.requestAndHandleSkinAndCape(entity, session, null);
            return entity;
        }

        Entity entity;
        if (packet.getType() == EntityType.FALLING_BLOCK) {
            entity = new FallingBlockEntity(session, packet.getEntityId(), session.getEntityCache().getNextEntityId().incrementAndGet(), packet.getUuid(),
                    position, motion, yaw, pitch, headYaw, ((FallingBlockData) packet.getData()).getId());
        } else if (packet.getType() == EntityType.ITEM_FRAME || packet.getType() == EntityType.GLOW_ITEM_FRAME) {
            // Item frames need the hanging direction
            entity = new ItemFrameEntity(session, packet.getEntityId(), session.getEntityCache().getNextEntityId().incrementAndGet(), packet.getUuid(),
                    definition, position, motion, yaw, pitch, headYaw, (Direction) packet.getData());
        } else if (packet.getType() == EntityType.PAINTING) {
            entity = new PaintingEntity(session, packet.getEntityId(), session.getEntityCache().getNextEntityId().incrementAndGet(), packet.getUuid(),
                    definition, position, motion, yaw, pitch, headYaw, (Direction) packet.getData());
        } else if (packet.getType() == EntityType.FISHING_BOBBER) {
            // Fishing bobbers need the owner for the line
            int ownerEntityId = ((ProjectileData) packet.getData()).getOwnerId();
            Entity owner = session.getEntityCache().getEntityByJavaId(ownerEntityId);
            // Java clients only spawn fishing hooks with a player as its owner
            if (owner instanceof PlayerEntity) {
                entity = new FishingHookEntity(session, packet.getEntityId(), session.getEntityCache().getNextEntityId().incrementAndGet(), packet.getUuid(),
                        position, motion, yaw, pitch, headYaw, (PlayerEntity) owner);
            } else {
                return null;
            }
        } else {
            entity = definition.factory().create(session, packet.getEntityId(), session.getEntityCache().getNextEntityId().incrementAndGet(),
                    packet.getUuid(), definition, position, motion, yaw, pitch, headYaw);
        }

        if (packet.getType() == EntityType.WARDEN) {
            WardenData wardenData = (WardenData) packet.getData();
            if (wardenData.isEmerging()) {
                entity.setPose(Pose.EMERGING);
            }
        }

        return entity;
    }
}