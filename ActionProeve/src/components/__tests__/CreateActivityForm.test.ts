import { mount } from '@vue/test-utils'
import CreateActivityForm from '../../components/CreateActivityForm.vue';
import { describe, expect, it } from 'vitest'

describe('AddActivityForm.vue', () => {
  it('renders the first step of the form', () => {
    const wrapper = mount(CreateActivityForm)

    const firstInput = wrapper.findComponent( {name:"BaseInput"});
    expect(firstInput.exists()).toBe(true);
    const inputElement = firstInput.find("input");

    expect(inputElement.attributes('placeholder')).toBe("fx: Badminton");
  })

  it('proceeds to the second step when "Next" is clicked', async () => {
    const wrapper = mount(CreateActivityForm)

    const firstInput = wrapper.findComponent({ name: "BaseInput" });
    await firstInput.setValue("New Activity");

    const secondInput = wrapper.find("textarea");
    await secondInput.setValue("activity description");

    const nextButton = wrapper.findComponent({ name: "BaseButton" });
    await nextButton.trigger("submit");
  
    // Wait for the DOM to update after the slide transition
    await wrapper.vm.$nextTick();

    const thirdInput = wrapper.findComponent({ name: "BaseInput" });
    expect(thirdInput.exists()).toBe(true);
    const inputElement = thirdInput.find("input");
    expect(inputElement.attributes("placeholder")).toBe("fx: 30, 45, 60")
  
  })

})
