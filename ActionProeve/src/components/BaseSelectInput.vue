<script setup lang="ts">
import {defineProps, defineEmits, computed} from 'vue';

interface Option {
  value: string;
  text: string;
}

const props = defineProps({
  id: String,
  name: String,
  labelText: String,
  placeholder: String,
  options: Array as () => Option[],
  modelValue: String
});

const emit = defineEmits(['update:modelValue']);

const updateValue = (event: any) => {
  emit('update:modelValue', event.target.value);
};
</script>

<template>
  <div class="input-group">
    <label :for="props.id">{{ props.labelText }}</label>
    <select :id="props.id" :name="props.name" v-model="props.modelValue" @input="updateValue">
      <option disabled value="">{{ props.placeholder }}</option>
      <option v-for="option in props.options" :key="option.value" :value="option.value">
        {{ option.text }}
      </option>
    </select>
  </div>
</template>

<style scoped>
.input-wrapper {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

label {
  font-weight: bold;
  margin-bottom: 0.5rem;

}

input,
select {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  transition: border-color 0.3s ease;
}

input:focus,
select:focus {
  border-color: #007bff;
  outline: none;
}
</style>