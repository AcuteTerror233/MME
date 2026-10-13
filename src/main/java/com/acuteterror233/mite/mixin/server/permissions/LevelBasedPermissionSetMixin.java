package com.acuteterror233.mite.mixin.server.permissions;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.Permissions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@link LevelBasedPermissionSet} — MME decouples entity-selector usage from operator
 * status. Vanilla ties the {@code Permissions#COMMANDS_ENTITY_SELECTORS} atom permission to the
 * gamemaster level inside this method, which gates selectors three times over: at selector
 * parsing ({@code EntitySelectorParser#allowSelectors}), at selector resolution
 * ({@code EntitySelector#checkPermissions}), and at client-side selector suggestions
 * ({@code EntityArgument#listSuggestions}). Granting the atom at every level lets non-operator
 * players use {@code @} selectors in any command they may already execute — most notably the
 * opened {@code /xp} query branches and bare {@code /xp} (which re-dispatches
 * {@code xp query @s points}) — while the per-command tree requirements keep every
 * operator-only command ({@code /xp add}, {@code /xp set}, {@code /kill}, ...) fully gated.
 * Sources at gamemaster level or above already passed this check, so their behaviour is
 * unchanged.
 */
@Mixin(LevelBasedPermissionSet.class)
public interface LevelBasedPermissionSetMixin {

    /**
     * Grants the entity-selector atom permission regardless of the set's level, so selector
     * parsing, resolution and suggestions all succeed for non-operator sources.
     */
    @Inject(
            method = "hasPermission(Lnet/minecraft/server/permissions/Permission;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void mme$grantEntitySelectors(final Permission permission, final CallbackInfoReturnable<Boolean> cir) {
        if (permission.equals(Permissions.COMMANDS_ENTITY_SELECTORS)) {
            cir.setReturnValue(true);
        }
    }
}
