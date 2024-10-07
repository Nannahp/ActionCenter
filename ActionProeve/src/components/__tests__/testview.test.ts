import { mount } from '@vue/test-utils'
import TestView from '../../views/test-view.vue'
import CreateActivityForm from '@/components/CreateActivityForm.vue'
import { describe, expect, it } from 'vitest'

describe('testview.vue', () => {
  it('renders Add New Acitivy Button'),
    () => {
      const wrapper = mount(TestView)
      expect(wrapper.find('button').text()).toBe('Add New Activity')
    }
  it('should display the CreateActivityForm when the button is clicked', async () => {
    const wrapper = mount(TestView)

    // Initially, the form is not visible
    expect(wrapper.findComponent(CreateActivityForm).exists()).toBe(false)

    // Click the button
    await wrapper.find('button').trigger('click')

    // The form should now be visible
    expect(wrapper.findComponent(CreateActivityForm).exists()).toBe(true)
  })
})
