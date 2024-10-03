import { mount } from '@vue/test-utils'
import TestView from 'c:/Users/nanna/Desktop/Dev/ActionProeve/ActionProeve/src/testview.vue'
import AddActivityForm from 'c:/Users/nanna/Desktop/Dev/ActionProeve/ActionProeve/src/components/AddActivityForm.vue';
import { describe, expect, it } from 'vitest'

describe('HomeAgain.vue', () => {
  it('renders Add New Acitivy Button'),
    () => {
      const wrapper = mount(TestView)
      expect(wrapper.find('button').text()).toBe('Add New Activity')
    }
  it('should display the AddActivityForm when the button is clicked', async () => {
    const wrapper = mount(TestView)

    // Initially, the form is not visible
    expect(wrapper.findComponent(AddActivityForm).exists()).toBe(false)

    // Click the button
    await wrapper.find('button').trigger('click')

    // The form should now be visible
    expect(wrapper.findComponent(AddActivityForm).exists()).toBe(true)
  })
})
