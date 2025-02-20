package agus4402.urumod.sound;

import agus4402.urumod.Urumod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS_EVENTS =
            DeferredRegister.create(
                    ForgeRegistries.SOUND_EVENTS,
                    Urumod.MOD_ID);


// * NEW SOUND EVENTS HERE * //

    public static final RegistryObject<SoundEvent> PAN_IDLE = registerSoundEvent("pan_idle");
    public static final RegistryObject<SoundEvent> PAN_ITEM_CONVERTED = registerSoundEvent("pan_item_converted");
    public static final RegistryObject<SoundEvent> PAN_PLACE = registerSoundEvent("pan_place");
    public static final RegistryObject<SoundEvent> PAN_METAL = registerSoundEvent("pan_metal");

    public static final ForgeSoundType PAN_SOUNDS = new
            ForgeSoundType(0.4f, 1f,
            ModSounds.PAN_PLACE, ModSounds.PAN_METAL,
            ModSounds.PAN_METAL,
            ModSounds.PAN_METAL, ModSounds.PAN_METAL);

// * --------------------- * //

// ? Register a sound event
// ? EXAMPLE:
// ?   public static final RegistryObject<SoundEvent> SOUND_BLOCK_BREAK =
// ?   registerSoundEvent("sound_block_break");

// ? Register a sound event to a block
// ? EXAMPLE:
// ?   public static final ForgeSoundType SOUND_BLOCK_SOUNDS = new
//?         ForgeSoundType(1f, 1f,
// ?           ModSounds.SOUND_BLOCK_BREAK, ModSounds.SOUND_BLOCK_STEP,
// ?           ModSounds.SOUND_BLOCK_PLACE,
// ?           ModSounds.SOUND_BLOCK_HIT, ModSounds.SOUND_BLOCK_FALL);

    public static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUNDS_EVENTS.register(
                name,
                () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Urumod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS_EVENTS.register(eventBus);
    }
}
