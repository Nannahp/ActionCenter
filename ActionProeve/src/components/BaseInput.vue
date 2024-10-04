<script setup lang="ts">
import { defineProps, defineEmits, computed } from 'vue'

// Define props
const props = defineProps<{
  labelText: string
  labelFor: string
  required?: boolean
  placeholder?: string
  id: string
  name: string
  type?: string
  modelValue: string | number // Accept string or number
  options?: Array<{ label: string; value: string | number }> // Options for select input
}>()

// Define emits
const emit = defineEmits(['update:modelValue'])

// Check if the input type is select
const isSelect = computed(() => props.type === 'select')

// Default input type is 'text'
const inputType = computed(() => props.type || 'text')

// Handle input change for text input
function handleInput(event: Event) {
  const target = event.target as HTMLInputElement
  emit('update:modelValue', target.value) // Emit updated value to parent
}

// Handle input change for select input
function handleChange(event: Event) {
  const target = event.target as HTMLSelectElement
  emit('update:modelValue', target.value) // Emit updated value to parent
}
</script>

<template>
  <div class="input-wrapper">
    <label :for="labelFor">{{ labelText }}</label>

    <!-- Render a select input if type is select -->
    <select v-if="isSelect" :id="id" :name="name" :required="required" @change="handleChange">
      <option v-for="option in options" :key="option.value" :value="option.value">
        {{ option.label }}
      </option>
    </select>

    <!-- Render a text input for all other types -->
    <input
      v-else
      :id="id"
      :name="name"
      :type="inputType"
      :required="required"
      :placeholder="placeholder"
      :value="modelValue"
      @input="handleInput"
    />
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
