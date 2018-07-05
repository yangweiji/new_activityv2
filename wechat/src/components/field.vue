<template>
  <div class="c-field-component" :class="[mode]">
    <div class="weui-cells__title">{{config.title}}<span v-if="config.required" style="color:red;font-size:10px;">(必填)</span></div>
    <div v-if="config.type == 'text'" class="weui-cells weui-cells_after-title">
      <div class="weui-cell weui-cell_input">
        <div class="weui-cell__bd">
          <input :disabled="disabled" @input="input" class="weui-input" :value="value" :placeholder="'请输入' + config.title" />
        </div>
      </div>
    </div>
    <div v-if="config.type == 'picker'" class="weui-cells weui-cells_after-title">
      <div class="weui-cell weui-cell_select">
        <div class="weui-cell__bd">
          <picker :placeholder="'请输入' + config.title" :disabled="disabled" :value="pickerIndex" @change="pickerChange" :range="config.options">
                  <div class="weui-select">{{value}}</div>
                </picker>
        </div>
      </div>
    </div>
    <div v-if="config.type == 'radio'" class="weui-cells weui-cells_after-title">
      <radio-group :disabled="disabled" @change="radioChange">
        <label class="weui-cell weui-check__label" v-for="item in options" :key="item.value">
                        <radio :disabled="disabled" class="weui-check" :value="item.value" :checked="item.checked" />
                        <div class="weui-cell__bd">{{item.title}}</div>
                        <div class="weui-cell__ft weui-cell__ft_in-radio" v-if="item.checked">
                          <icon class="weui-icon-radio" type="success_no_circle" size="16"></icon>
                        </div>
                      </label>
      </radio-group>
    </div>
    <div v-if="config.type == 'check'" class="weui-cells weui-cells_after-title">
      <checkbox-group :disabled="disabled" @change="checkChange">
        <label class="weui-cell weui-check__label" v-for="item in options" :key="item.value">
                        <checkbox :disabled="disabled" class="weui-check" :value="item.value" :checked="item.checked" />
                        <div class="weui-cell__hd weui-check__hd_in-checkbox">
                                    <icon class="weui-icon-checkbox_circle" type="circle" size="23" v-if="!item.checked"></icon>
                                    <icon class="weui-icon-checkbox_success" type="success" size="23" v-if="item.checked"></icon>
                                  </div>
                                  <div class="weui-cell__bd">{{item.title}}</div>
                      </label>
      </checkbox-group>
    </div>
    <div v-if="config.type == 'bool'" class="weui-cells weui-cells_after-title">
      <div class="weui-cell weui-cell_switch">
        <div class="weui-cell__bd">{{config.title}}</div>
        <div class="weui-cell__ft">
          <switch :disabled="disabled" :checked="value" @change="input" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    props: ["config", "value", "disabled", 'mode'],
    data() {
      return {
        options: null
      };
    },
    computed: {
      pickerIndex(){
        if(this.config && this.config.type == 'picker'){
          var index = this.config.options.indexOf(this.value)
          if(index >= 0 ){
            return index
          }
        }
        return 0
      }
    },
    methods: {
      input(e) {
        this.$emit("input", e.mp.detail.value);
      },
      radioChange(e) {
        this.$emit("input", e.mp.detail.value);
      },
      checkChange(e) {
        this.$emit("input", e.mp.detail.value.join());
      },
      pickerChange(e){
        this.$emit("input", this.config.options[e.mp.detail.value])
      },
      resetValue() {
        if (this.options && this.config) {
          for (var i = 0; i < this.options.length; i++) {
            var option = this.options[i];
            if (this.config.type == "radio") {
              option.checked = option.value == this.value;
            } else if (this.config.type == "check") {
              option.checked =
                this.value && this.value.split(",").indexOf(option.value) >= 0;
            }
          }
        }
      },
      resetConfig() {
        var options = [];
        if (this.config && this.config.options) {
          for (var i = 0; i < this.config.options.length; i++) {
            var option = this.config.options[i];
            var opt = {
              title: option.title,
              value: option.value
            };
            if (this.config.type == "radio") {
              opt.checked = option.value == this.value;
            } else if (this.config.type == "check") {
              opt.checked =
                this.value && this.value.split(",").indexOf(option.value) >= 0;
            }
            options.push(opt);
          }
        }
        this.options = options;
      }
    },
    created() {
      this.resetConfig()
      this.resetValue()
    },
    watch: {
      value() {
        this.resetValue()
      },
      config() {
        this.resetConfig()
      }
    }
  };
</script>

<style>

</style>
