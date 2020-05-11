package com.github.nijian.jkeel.concept.runtime;

import com.github.nijian.jkeel.concept.ConfigItem;

public interface BehaviorListener {
    void onStart(ConfigItem<?> configItem);

    void onEnd(ConfigItem<?> configItem);
}
