import {writable} from "svelte/store";

export const isOpen = writable(false);
export const formType = writable('');

const initialValue = {
    name:"",
    executionStart:"",
    executionEnd:"",
    rating:0,
    type:"",
    phase:0,
    auditor:"",
    code:"",
    keywords:[],
    isActive:false,
    inCooperation:false,
};
export const formData = writable({...initialValue});
export function resetFormDateStore() {
    formData.set({...initialValue});
}

export const TYPE = Object.freeze({
    CREATE: 'CREATE',
    UPDATE: 'UPDATE',
});